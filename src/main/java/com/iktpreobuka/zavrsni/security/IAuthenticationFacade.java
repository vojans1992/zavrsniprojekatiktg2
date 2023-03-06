package com.iktpreobuka.zavrsni.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

	Authentication getAuthentication();
}
