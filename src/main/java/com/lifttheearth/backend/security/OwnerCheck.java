package com.lifttheearth.backend.security;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OwnerCheck {
}
