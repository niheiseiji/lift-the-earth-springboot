// com.lifttheearth.backend.security.TrainingOwnerAspect.java
package com.lifttheearth.backend.security;

import com.lifttheearth.backend.domain.Training;
import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TrainingOwnerAspect {

    private final TrainingRepository trainingRepository;

    // @OwnerCheck が付与されたメソッドで、Long id を引数にもつものに限定
    @Before("@annotation(com.lifttheearth.backend.security.OwnerCheck) && args(id,..)")
    public void checkOwnership(JoinPoint joinPoint, Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User user)) {
            throw new AccessDeniedException("Authentication principal is not User");
        }

        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));

        if (!training.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("Not owner of training id=" + id);
        }
    }
}
