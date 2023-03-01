package com.iktpreobuka.zavrsni.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.dto.EmailDto;
@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendSimpleMessage(EmailDto emailDTO) {
		// 1. Create a new message
		SimpleMailMessage message = new SimpleMailMessage();
		// 2. Set to, subject, text of the created message
		message.setTo(emailDTO.getTo());
		message.setSubject(emailDTO.getSubject());
		message.setText(emailDTO.getText());
		// TODO 3. Send the message
		emailSender.send(message);

	}

	@Override
	public void sendTemplateMessage(EmailDto emailDTO) {
		// 1. Create a new message
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// 2. Set to, subject, text of the created message
			helper.setTo(emailDTO.getTo());
			helper.setSubject(emailDTO.getSubject());
			helper.setText(emailDTO.getText(), true);
			// TODO 3. Send the message
			emailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessageWithAttachment(EmailDto emailDTO, String pathToAttachment) {
		// 1. Create a new message
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// 2. Set to, subject, text of the created message
			helper.setTo(emailDTO.getTo());
			helper.setSubject(emailDTO.getSubject());
			helper.setText(emailDTO.getText(), true);
			// 2.1 add the attachment
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment(file.getFilename(), file);
			// TODO 3. Send the message
			emailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
