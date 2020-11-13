package com.capstone.helper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.SendersAndReceivers;
import com.capstone.helper.service.SenderReceiverService;
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
	private SenderReceiverService senderReceiverService;
	
	@MessageMapping("/sendTo")
	@SendTo("/topics/sendTo")
	public String SendToMessage() throws Exception {
		return "SendTo";
	}
	
	@MessageMapping("/Template")
	public void SendTemplateMessage() {
		webSocket.convertAndSend("/topics/template" , "Template");
	}
	
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
		java.util.List<SendersAndReceivers> receiverList = senderReceiverService.findBySenderId(FallEventVo.getUserId());
		FallAlarmVo nonActiveAlarmVo = new FallAlarmVo(FallEventVo.getUserId(),"fall",FallEventVo.getTimestamp(),FallEventVo.getLongitude(),FallEventVo.getLatitude());

		//send Alarm
		for(SendersAndReceivers senderReceiver : receiverList) {
			broadcastNonActiveAlarm(senderReceiver.getReceiverId(), nonActiveAlarmVo);
		}

		
	}
	
	public void broadcastNonActiveAlarm(int receiverId, FallAlarmVo nonActiveAlarmVo) {
		webSocket.convertAndSend("/topics/" + Integer.toString(receiverId) ,nonActiveAlarmVo);
	}
	
	
	
	
}

