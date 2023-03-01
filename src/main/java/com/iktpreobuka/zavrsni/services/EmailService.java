package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.dto.EmailDto;

public interface EmailService {

	public void sendSimpleMessage(EmailDto emailDTO);
	public void sendTemplateMessage(EmailDto emailDTO);
	public void sendMessageWithAttachment(EmailDto emailDTO, String pathToAttachment);
}
