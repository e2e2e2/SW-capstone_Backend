package com.capstone.helper.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.Alarm;
import com.capstone.helper.model.AlarmType;
import com.capstone.helper.model.FallEvent;
import com.capstone.helper.model.Home;
import com.capstone.helper.model.HomeInEvent;
import com.capstone.helper.model.HomeOutEvent;
import com.capstone.helper.model.NonActiveEvent;
import com.capstone.helper.model.ReceiverEnvironment;
import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.model.Token;
import com.capstone.helper.model.User;
import com.capstone.helper.service.AlarmService;
import com.capstone.helper.service.AlarmTypeService;
import com.capstone.helper.service.FCMService;
import com.capstone.helper.service.FallEventService;
import com.capstone.helper.service.HomeInEventService;
import com.capstone.helper.service.HomeOutEventService;
import com.capstone.helper.service.HomeService;
import com.capstone.helper.service.NonActiveEventService;
import com.capstone.helper.service.ReceiverEnvironmentService;
import com.capstone.helper.service.SendersAndReceiversService;
import com.capstone.helper.service.TokenService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.AlarmTypeVo;
import com.capstone.helper.vo.FallAlarmVo;
import com.capstone.helper.vo.FallEventVo;
import com.capstone.helper.vo.HomeInAlarmVo;
import com.capstone.helper.vo.HomeInEventVo;
import com.capstone.helper.vo.HomeOutAlarmVo;
import com.capstone.helper.vo.HomeOutEventVo;
import com.capstone.helper.vo.NonActiveAlarmVo;
import com.capstone.helper.vo.NonActiveEventVo;
import com.google.firebase.messaging.FirebaseMessagingException;


@RestController
public class AlarmController {
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SendersAndReceiversService senderReceiverService;
	
	@Autowired
	private FallEventService fallEventService;
	
	@Autowired
	private NonActiveEventService nonActiveEventService;
	
	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private AlarmTypeService alarmTypeService;
	
	@Autowired
	private FCMService fCMService;
	
	@Autowired
	private ReceiverEnvironmentService receiverEnvironmentService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private HomeInEventService homeInEventService;
	
	@Autowired
	private HomeOutEventService homeOutEventService;
	
	@RequestMapping(value="/fall/user/alarm", method=RequestMethod.POST)
	public void requestFallAlarm(@RequestBody FallEventVo fallEventVo) throws FirebaseMessagingException {
		//process json input
		
		if(userService.findOne(fallEventVo.getUserId()) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("fall");
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(fallEventVo.getUserId(),alarmType.getId());
		FallAlarmVo fallAlarmVo = new FallAlarmVo(fallEventVo.getUserId(),"fall",fallEventVo.getTimestamp(),fallEventVo.getLongitude(),fallEventVo.getLatitude());

		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		//log event at db
		FallEvent fallEvent = new FallEvent(fallEventVo.getUserId() , fallEventVo.getLongitude(), fallEventVo.getLatitude(), fallEventVo.getTimestamp());
		fallEventService.save(fallEvent);
		
		
		
		//send Alarm and to web and log at db
		for(SenderAndReceiver senderReceiver : receiverList) {
			sendFallAlarm(senderReceiver.getReceiverId(), fallAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), fallEvent.getId(), fallEventVo.getUserId(), senderReceiver.getReceiverId(), fallEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendFallPush(senderReceiver.getReceiverId(), fallAlarmVo);
					
			Alarm alarm = new Alarm(alarmType.getId(), fallEvent.getId(), fallEventVo.getUserId(), senderReceiver.getReceiverId(), fallEventVo.getTimestamp());
			alarmService.save(alarm);
		}		
	}
	
	@RequestMapping(value="/non-active/user/alarm", method=RequestMethod.POST)
	public void requestNonActiveAlarm(@RequestBody NonActiveEventVo nonActiveEventVo) throws FirebaseMessagingException {
		//process json input
		
		if(userService.findOne(nonActiveEventVo.getUserId()) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("nonactive");
		
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(nonActiveEventVo.getUserId(),alarmType.getId());
		NonActiveAlarmVo nonActiveAlarmVo = new NonActiveAlarmVo(nonActiveEventVo.getUserId(),"nonactive",nonActiveEventVo.getTimestamp(),nonActiveEventVo.getLongitude(),nonActiveEventVo.getLatitude());
		
		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		//log event at db
		NonActiveEvent nonActiveEvent = new NonActiveEvent(nonActiveEventVo.getUserId() , nonActiveEventVo.getLongitude(), nonActiveEventVo.getLatitude(), nonActiveEventVo.getTimestamp());
		nonActiveEventService.save(nonActiveEvent);
		
	
		
		
		//send Alarm to web and log at db
		for(SenderAndReceiver senderReceiver : receiverWebList) {
			sendNonActiveAlarm(senderReceiver.getReceiverId(), nonActiveAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), nonActiveEvent.getId(), nonActiveEventVo.getUserId(), senderReceiver.getReceiverId(), nonActiveEventVo.getTimestamp());
			alarmService.save(alarm);
		}
		
		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendNonActivePush(senderReceiver.getReceiverId(), nonActiveAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), nonActiveEvent.getId(), nonActiveEventVo.getUserId(), senderReceiver.getReceiverId(), nonActiveEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		
	}
	@RequestMapping(value="/home-in/user/alarm", method=RequestMethod.POST)
	public void requestHomeInAlarm(@RequestBody HomeInEventVo homeInEventVo) throws FirebaseMessagingException {
		//process json input
		
		if(userService.findOne(homeInEventVo.getUserId()) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("homein");
		
		//find Home info.
		Home home = homeService.findByUserId(homeInEventVo.getUserId());
		
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(homeInEventVo.getUserId(),alarmType.getId());
		HomeInAlarmVo homeInAlarmVo = new HomeInAlarmVo(homeInEventVo.getUserId(),"homein",homeInEventVo.getTimestamp(), home.getLongitude(), home.getLatitude());
		
		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		
		//log event at db
		HomeInEvent homeInEvent = new HomeInEvent(homeInEventVo.getUserId() , home.getId(), homeInEventVo.getTimestamp());
		homeInEventService.save(homeInEvent);
		
	
		
		
		//send Alarm to web and log at db
		for(SenderAndReceiver senderReceiver : receiverWebList) {
			sendHomeInAlarm(senderReceiver.getReceiverId(), homeInAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeInEvent.getId(), homeInEvent.getUserId(), senderReceiver.getReceiverId(), homeInEvent.getTimestamp());
			alarmService.save(alarm);
		}
		
		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendHomeInPush(senderReceiver.getReceiverId(), homeInAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeInEvent.getId(), homeInEvent.getUserId(), senderReceiver.getReceiverId(), homeInEvent.getTimestamp());
			alarmService.save(alarm);
		}

		
	}
	@RequestMapping(value="/home-out/user/alarm", method=RequestMethod.POST)
	public void requestHomeOutAlarm(@RequestBody HomeOutEventVo homeOutEventVo) throws FirebaseMessagingException {
		//process json input
		
		if(userService.findOne(homeOutEventVo.getUserId()) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("homeout");
		
		//find Home info.
		Home home = homeService.findByUserId(homeOutEventVo.getUserId());
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(homeOutEventVo.getUserId(),alarmType.getId());
		HomeOutAlarmVo homeOutAlarmVo = new HomeOutAlarmVo(homeOutEventVo.getUserId(),"homeout",homeOutEventVo.getTimestamp(),home.getLongitude(),home.getLatitude());
		
		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		//log event at db
		HomeOutEvent homeOutEvent = new HomeOutEvent(homeOutEventVo.getUserId() , home.getId(), homeOutEventVo.getTimestamp());
		homeOutEventService.save(homeOutEvent);
		
	
		
		
		//send Alarm to web and log at db
		for(SenderAndReceiver senderReceiver : receiverWebList) {
			sendHomeOutAlarm(senderReceiver.getReceiverId(), homeOutAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeOutEvent.getId(), homeOutEventVo.getUserId(), senderReceiver.getReceiverId(), homeOutEventVo.getTimestamp());
			alarmService.save(alarm);
		}
		
		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendHomeOutPush(senderReceiver.getReceiverId(), homeOutAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeOutEvent.getId(), homeOutEventVo.getUserId(), senderReceiver.getReceiverId(), homeOutEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		
	}
	
	@RequestMapping(value="/alarm-type", method=RequestMethod.POST)
	public void saveAlarmType(@RequestBody AlarmTypeVo alarmTypeVo) {
		AlarmType alarmType = new AlarmType(alarmTypeVo.getAlarmName());
		alarmTypeService.save(alarmType); 
	}
	
	@RequestMapping(value="/alarm-type", method=RequestMethod.GET)
	public ResponseEntity<List<AlarmType>> getAlarmTypes() {
		List<AlarmType> alarmTypes = alarmTypeService.findAll();
		return new ResponseEntity<List<AlarmType>>(alarmTypes,HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}/alarm", method=RequestMethod.GET)
	public ResponseEntity<List<Alarm>> getRecentAlarms(@PathVariable("id") int id) {
		List<Alarm> alarms = alarmService.findByReceiverId(id);
		if(alarms.size()>=10) {
			alarms = alarms.subList(alarms.size()-10,alarms.size());
		}
		Collections.reverse(alarms);
		
		return new ResponseEntity<List<Alarm>>(alarms,HttpStatus.OK);
	}
	
	public void sendFallAlarm(int receiverId, FallAlarmVo fallAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) ,fallAlarmVo);
	}
	
	public void sendNonActiveAlarm(int receiverId, NonActiveAlarmVo nonActiveAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) , nonActiveAlarmVo);
	}
	public void sendHomeInAlarm(int receiverId, HomeInAlarmVo homeInAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) , homeInAlarmVo);
	}
	public void sendHomeOutAlarm(int receiverId, HomeOutAlarmVo homeOutAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) , homeOutAlarmVo);
	}
	
	public void sendFallPush(int receiverId, FallAlarmVo fallAlarmVo) throws FirebaseMessagingException {
		List<Token> tokenList = tokenService.findByUserId(receiverId);
		User user = userService.findOne(fallAlarmVo.getSenderId());
		Map<String, String> map= new HashMap<>();
		
		map.put("senderId", Integer.toString(fallAlarmVo.getSenderId()));
		map.put("senderName",user.getName());
		map.put("alarmType",fallAlarmVo.getAlarmType());
		map.put("timestamp", fallAlarmVo.getTimestamp().toString());
		map.put("longitude", Float.toString(fallAlarmVo.getLongitude()));
		map.put("latitude", Float.toHexString(fallAlarmVo.getLatitude()));
		for(Token token: tokenList) {
			try {
				fCMService.send(token.getToken(), map);
			}
			catch(Exception e) {
			}
		}
	}
	
	public void sendNonActivePush(int receiverId, NonActiveAlarmVo nonActiveAlarmVo) throws FirebaseMessagingException {
		List<Token> tokenList = tokenService.findByUserId(receiverId);
		User user = userService.findOne(nonActiveAlarmVo.getSenderId());
		Map<String, String> map= new HashMap<>();
		
		map.put("senderId", Integer.toString(nonActiveAlarmVo.getSenderId()));
		map.put("senderName",user.getName());
		map.put("alarmType",nonActiveAlarmVo.getAlarmType());
		map.put("timestamp", nonActiveAlarmVo.getTimestamp().toString());
		map.put("longitude", Float.toString(nonActiveAlarmVo.getLongitude()));
		map.put("latitude", Float.toString(nonActiveAlarmVo.getLatitude()));
		for(Token token: tokenList) {
			try {
				fCMService.send(token.getToken(), map);
			}
			catch(Exception e) {
			}
		}
	}
	public void sendHomeInPush(int receiverId, HomeInAlarmVo homeInAlarmVo) throws FirebaseMessagingException {
		List<Token> tokenList = tokenService.findByUserId(receiverId);
		User user = userService.findOne(homeInAlarmVo.getSenderId());
		Map<String, String> map= new HashMap<>();
		
		map.put("senderId", Integer.toString(homeInAlarmVo.getSenderId()));
		map.put("senderName",user.getName());
		map.put("alarmType",homeInAlarmVo.getAlarmType());
		map.put("timestamp", homeInAlarmVo.getTimestamp().toString());
		map.put("longitude", Float.toString(homeInAlarmVo.getLongitude()));
		map.put("latitude", Float.toString(homeInAlarmVo.getLatitude()));
		for(Token token: tokenList) {
			try {
				fCMService.send(token.getToken(), map);
			}
			catch(Exception e) {
			}
		}
	}
	public void sendHomeOutPush(int receiverId, HomeOutAlarmVo homeOutAlarmVo) throws FirebaseMessagingException {
		List<Token> tokenList = tokenService.findByUserId(receiverId);
		User user = userService.findOne(homeOutAlarmVo.getSenderId());
		Map<String, String> map= new HashMap<>();
		
		map.put("senderId", Integer.toString(homeOutAlarmVo.getSenderId()));
		map.put("senderName",user.getName());
		map.put("alarmType",homeOutAlarmVo.getAlarmType());
		map.put("timestamp", homeOutAlarmVo.getTimestamp().toString());
		map.put("longitude", Float.toString(homeOutAlarmVo.getLongitude()));
		map.put("latitude", Float.toString(homeOutAlarmVo.getLatitude()));
		for(Token token: tokenList) {
			try {
				fCMService.send(token.getToken(), map);
			}
			catch(Exception e) {
			}
		}
	}
	
	public List<SenderAndReceiver> getReceiverListByHasAppHasWeb(List<SenderAndReceiver> receiverList, boolean hasWeb, boolean hasApp ){
		java.util.List<SenderAndReceiver> resultList = new ArrayList<>();
		if(hasApp && hasWeb) {
			return null;
		}
		if(receiverList.size() != 0) {
			for (SenderAndReceiver senderAndReceiver : receiverList) {
				int userId = senderAndReceiver.getReceiverId();
				ReceiverEnvironment receiverEnvironment = receiverEnvironmentService.findByUserId(userId);
				if(hasApp && receiverEnvironment.getHasApp()) {
					resultList.add(senderAndReceiver);
				}
				if(hasWeb && receiverEnvironment.getHasWeb()) {
					resultList.add(senderAndReceiver);
				}
				
			}
		}
		return resultList;
	}
	
}

