package com.capstone.helper.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.capstone.helper.vo.AlarmVo;
import com.capstone.helper.vo.FallEventVo;
import com.capstone.helper.vo.HomeInEventVo;
import com.capstone.helper.vo.HomeOutEventVo;
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
	public int requestFallAlarm(HttpServletRequest request, @RequestBody FallEventVo fallEventVo) throws FirebaseMessagingException {
		//process json input

		
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		
		if(userService.findOne(numID) == null) {
			return -1;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("fall");
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(numID,alarmType.getId());
		AlarmVo alarmVo = new AlarmVo(numID,"fall",fallEventVo.getTimestamp(),fallEventVo.getLongitude(),fallEventVo.getLatitude());

		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		//log event at db
		FallEvent fallEvent = new FallEvent(numID , fallEventVo.getLongitude(), fallEventVo.getLatitude(), fallEventVo.getTimestamp());
		fallEventService.save(fallEvent);
		
		
		
		//send Alarm and to web and log at db
		for(SenderAndReceiver senderReceiver : receiverList) {
			sendAlarm(senderReceiver.getReceiverId(), alarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), fallEvent.getId(), numID, senderReceiver.getReceiverId(), fallEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendPush(senderReceiver.getReceiverId(), alarmVo);
					
			Alarm alarm = new Alarm(alarmType.getId(), fallEvent.getId(), numID, senderReceiver.getReceiverId(), fallEventVo.getTimestamp());
			alarmService.save(alarm);
		}
		
		return fallEvent.getId();
	}
	
	@RequestMapping(value="/non-active/user/alarm", method=RequestMethod.POST)
	public Integer requestNonActiveAlarm(HttpServletRequest request, @RequestBody NonActiveEventVo nonActiveEventVo) throws FirebaseMessagingException {

		
		//process json input
		
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		
		
		if(userService.findOne(numID) == null) {
			return -1;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("nonactive");
		
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(numID,alarmType.getId());
		AlarmVo alarmVo = new AlarmVo(numID,"nonactive",nonActiveEventVo.getTimestamp(),nonActiveEventVo.getLongitude(),nonActiveEventVo.getLatitude());
		
		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		//log event at db
		NonActiveEvent nonActiveEvent = new NonActiveEvent(numID , nonActiveEventVo.getLongitude(), nonActiveEventVo.getLatitude(), nonActiveEventVo.getTimestamp());
		nonActiveEventService.save(nonActiveEvent);
		
	
		
		
		//send Alarm to web and log at db
		for(SenderAndReceiver senderReceiver : receiverWebList) {
			sendAlarm(senderReceiver.getReceiverId(), alarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), nonActiveEvent.getId(), numID, senderReceiver.getReceiverId(), nonActiveEventVo.getTimestamp());
			alarmService.save(alarm);
		}
		
		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendPush(senderReceiver.getReceiverId(), alarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), nonActiveEvent.getId(), numID, senderReceiver.getReceiverId(), nonActiveEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		return nonActiveEvent.getId();
	}
	@RequestMapping(value="/home-in/user/alarm", method=RequestMethod.POST)
	public void requestHomeInAlarm(HttpServletRequest request, @RequestBody HomeInEventVo homeInEventVo) throws FirebaseMessagingException {
		//process json input
		
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		if(userService.findOne(numID) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("homein");
		
		//find Home info.
		Home home = homeService.findByUserId(numID);
		
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(numID,alarmType.getId());
		AlarmVo alarmVo = new AlarmVo(numID,"homein",homeInEventVo.getTimestamp(), home.getLongitude(), home.getLatitude());
		
		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		
		//log event at db
		HomeInEvent homeInEvent = new HomeInEvent(numID , home.getId(), homeInEventVo.getTimestamp());
		homeInEventService.save(homeInEvent);
		
	
		
		
		//send Alarm to web and log at db
		for(SenderAndReceiver senderReceiver : receiverWebList) {
			sendAlarm(senderReceiver.getReceiverId(), alarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeInEvent.getId(), homeInEvent.getUserId(), senderReceiver.getReceiverId(), homeInEvent.getTimestamp());
			alarmService.save(alarm);
		}
		
		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendPush(senderReceiver.getReceiverId(), alarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeInEvent.getId(), homeInEvent.getUserId(), senderReceiver.getReceiverId(), homeInEvent.getTimestamp());
			alarmService.save(alarm);
		}

		
	}
	@RequestMapping(value="/home-out/user/alarm", method=RequestMethod.POST)
	public void requestHomeOutAlarm(HttpServletRequest request, @RequestBody HomeOutEventVo homeOutEventVo) throws FirebaseMessagingException {
		//process json input
		
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		if(userService.findOne(numID) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("homeout");
		
		//find Home info.
		Home home = homeService.findByUserId(numID);
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(numID,alarmType.getId());
		AlarmVo alarmVo = new AlarmVo(numID,"homeout",homeOutEventVo.getTimestamp(),home.getLongitude(),home.getLatitude());
		
		java.util.List<SenderAndReceiver> receiverWebList = getReceiverListByHasAppHasWeb(receiverList, true,false);
		java.util.List<SenderAndReceiver> receiverAppList = getReceiverListByHasAppHasWeb(receiverList, false,true);
		
		//log event at db
		HomeOutEvent homeOutEvent = new HomeOutEvent(numID , home.getId(), homeOutEventVo.getTimestamp());
		homeOutEventService.save(homeOutEvent);
		
	
		
		
		//send Alarm to web and log at db
		for(SenderAndReceiver senderReceiver : receiverWebList) {
			sendAlarm(senderReceiver.getReceiverId(), alarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeOutEvent.getId(), numID, senderReceiver.getReceiverId(), homeOutEventVo.getTimestamp());
			alarmService.save(alarm);
		}
		
		//send push to app and log at db
		for(SenderAndReceiver senderReceiver : receiverAppList) {
			//send push alarm
			sendPush(senderReceiver.getReceiverId(), alarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), homeOutEvent.getId(), numID, senderReceiver.getReceiverId(), homeOutEventVo.getTimestamp());
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
	
	//ID를 빼고 세션 연결에 의존
	@RequestMapping(value="/user/alarm", method=RequestMethod.GET)
	public ResponseEntity<List<Alarm>> getRecentAlarms(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		List<Alarm> alarms = alarmService.findByReceiverId(numID);
		if(alarms.size()>=10) {
			alarms = alarms.subList(alarms.size()-10,alarms.size());
		}
		Collections.reverse(alarms);
		
		return new ResponseEntity<List<Alarm>>(alarms,HttpStatus.OK);
	}
	
	public void sendAlarm(int receiverId, AlarmVo alarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) , alarmVo);
	}
	
	public void sendPush(int receiverId, AlarmVo alarmVo) throws FirebaseMessagingException {
		List<Token> tokenList = tokenService.findByUserId(receiverId);
		User user = userService.findOne(alarmVo.getSenderId());
		Map<String, String> map= new HashMap<>();
		
		map.put("senderId", Integer.toString(alarmVo.getSenderId()));
		map.put("senderName",user.getName());
		map.put("alarmType",alarmVo.getAlarmType());
		map.put("timestamp", alarmVo.getTimestamp().toString());
		map.put("longitude", Float.toString(alarmVo.getLongitude()));
		map.put("latitude", Float.toString(alarmVo.getLatitude()));
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

