package com.capstone.helper.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service("fCMService")
public class FCMService {

	public void send(String token, Map<String, String> map) throws FirebaseMessagingException {
		
		// See documentation on defining a message payload.
		Message message = Message.builder()
				.putAllData(map)
				.setToken(token)
				.build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response = FirebaseMessaging.getInstance().send(message);
		// Response is a message ID string.
		System.out.println("Successfully sent message: " + response);		
	}
}
