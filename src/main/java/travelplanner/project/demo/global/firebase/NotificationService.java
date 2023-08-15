package travelplanner.project.demo.global.firebase;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private final Map<Long, String> fireBaseToken = new HashMap<>();

    public void createToken(final Long userId, final String token) {
        fireBaseToken.put(userId, token);
    }

    public void deleteToken (final Long userId, final String token) {
        fireBaseToken.remove(userId, token);
    }
}
