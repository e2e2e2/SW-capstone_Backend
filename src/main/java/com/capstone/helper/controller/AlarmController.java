package com.capstone.helper.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.Alarm;
import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.service.AlarmService;
import com.capstone.helper.service.SendersAndReceiversService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.FallAlarmVo;
import com.capstone.helper.vo.FallEventVo;


@RestController
public class AlarmController {
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SendersAndReceiversService senderReceiverService;
	
	@Autowired
	private AlarmService alarmService;
	
	@RequestMapping(value="/fall/user/{id}/alarm", method=RequestMethod.POST)
	public void requestNonActiveAlarm(@RequestBody FallEventVo FallEventVo , @PathVariable("id") int userId) {
		//process json input
		
		System.out.println(FallEventVo.getUserId());
		System.out.println(FallEventVo.getTimestamp());
		System.out.println(FallEventVo.getLatitude());
		
		if(userService.findOne(FallEventVo.getUserId()) == null) {
			return ;
		}
		
		// get receiver list from DB by sender_id
		java.util.List<SenderAndReceiver> receiverList = senderReceiverService.findBySenderId(FallEventVo.getUserId());
		FallAlarmVo nonActiveAlarmVo = new FallAlarmVo(FallEventVo.getUserId(),"fall",FallEventVo.getTimestamp(),FallEventVo.getLongitude(),FallEventVo.getLatitude());

		
		//log event at db
		
		
		//send Alarm and log at db
		for(SenderAndReceiver senderReceiver : receiverList) {
			broadcastNonActiveAlarm(senderReceiver.getReceiverId(), nonActiveAlarmVo);
			
			
			Alarm alarm = new Alarm(1, -1, FallEventVo.getUserId(), senderReceiver.getReceiverId(), FallEventVo.getTimestamp());
			alarmService.save(alarm);
		}

		
	}
	
	public void broadcastNonActiveAlarm(int receiverId, FallAlarmVo nonActiveAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) ,nonActiveAlarmVo);
	}
	
	
	
	
}

