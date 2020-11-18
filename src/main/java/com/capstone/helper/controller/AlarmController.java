package com.capstone.helper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.Alarm;
import com.capstone.helper.model.AlarmType;
import com.capstone.helper.model.FallEvent;
import com.capstone.helper.model.NonActiveEvent;
import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.service.AlarmService;
import com.capstone.helper.service.AlarmTypeService;
import com.capstone.helper.service.FallEventService;
import com.capstone.helper.service.NonActiveEventService;
import com.capstone.helper.service.SendersAndReceiversService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.AlarmTypeVo;
import com.capstone.helper.vo.FallAlarmVo;
import com.capstone.helper.vo.FallEventVo;
import com.capstone.helper.vo.NonActiveAlarmVo;
import com.capstone.helper.vo.NonActiveEventVo;


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
	
	@RequestMapping(value="/fall/user/{id}/alarm", method=RequestMethod.POST)
	public void requestFallAlarm(@RequestBody FallEventVo fallEventVo , @PathVariable("id") int userId) {
		//process json input
		
		if(userService.findOne(fallEventVo.getUserId()) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("fall");
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(fallEventVo.getUserId(),alarmType.getId());
		FallAlarmVo fallAlarmVo = new FallAlarmVo(fallEventVo.getUserId(),"fall",fallEventVo.getTimestamp(),fallEventVo.getLongitude(),fallEventVo.getLatitude());

		
		//log event at db
		FallEvent fallEvent = new FallEvent(fallEventVo.getUserId() , fallEventVo.getLongitude(), fallEventVo.getLatitude(), fallEventVo.getTimestamp());
		fallEventService.save(fallEvent);
		
		
		
		
		//send Alarm and log at db
		for(SenderAndReceiver senderReceiver : receiverList) {
			broadcastFallAlarm(senderReceiver.getReceiverId(), fallAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), fallEvent.getId(), fallEventVo.getUserId(), senderReceiver.getReceiverId(), fallEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		
	}
	
	@RequestMapping(value="/non-active/user/{id}/alarm", method=RequestMethod.POST)
	public void requestNonActiveAlarm(@RequestBody NonActiveEventVo nonActiveEventVo , @PathVariable("id") int userId) {
		//process json input
		
		if(userService.findOne(nonActiveEventVo.getUserId()) == null) {
			return ;
		}
		
		//get alarmtype id by string
		AlarmType alarmType = alarmTypeService.findByAlarmName("nonactive");
		
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(nonActiveEventVo.getUserId(),alarmType.getId());
		NonActiveAlarmVo nonActiveAlarmVo = new NonActiveAlarmVo(nonActiveEventVo.getUserId(),"nonactive",nonActiveEventVo.getTimestamp(),nonActiveEventVo.getLongitude(),nonActiveEventVo.getLatitude());

		
		//log event at db
		NonActiveEvent nonActiveEvent = new NonActiveEvent(nonActiveEventVo.getUserId() , nonActiveEventVo.getLongitude(), nonActiveEventVo.getLatitude(), nonActiveEventVo.getTimestamp());
		nonActiveEventService.save(nonActiveEvent);
		
	
		
		
		//send Alarm and log at db
		for(SenderAndReceiver senderReceiver : receiverList) {
			broadcastNonActiveAlarm(senderReceiver.getReceiverId(), nonActiveAlarmVo);
			
			Alarm alarm = new Alarm(alarmType.getId(), nonActiveEvent.getId(), nonActiveEventVo.getUserId(), senderReceiver.getReceiverId(), nonActiveEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		
	}
	
	@RequestMapping(value="/alarm-type", method=RequestMethod.POST)
	public void saveAlarmType(@RequestBody AlarmTypeVo alarmTypeVo) {
		AlarmType alarmType = new AlarmType(alarmTypeVo.getAlarmName());
		alarmTypeService.save(alarmType); 
	}
	
	
	public void broadcastFallAlarm(int receiverId, FallAlarmVo fallAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) ,fallAlarmVo);
	}
	
	public void broadcastNonActiveAlarm(int receiverId, NonActiveAlarmVo nonActiveAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) , nonActiveAlarmVo);
	}
	
	
	
}

