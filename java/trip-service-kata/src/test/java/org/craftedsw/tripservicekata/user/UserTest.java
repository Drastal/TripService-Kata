package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;

public class UserTest {

    private static final User BOB = new User();
    private static final User PAUL = new User();

    @Test
    public void shouldReturnFalse_whenNotFriends() {
        // Given
        User user = aUser().friendsWith(BOB).build();

        // When
        boolean result = user.isFriendsWith(PAUL);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrue_whenNotFriends() {
        // Given
        User user = aUser().friendsWith(BOB, PAUL).build();

        // When
        boolean result = user.isFriendsWith(PAUL);

        // Then
        assertThat(result).isTrue();
    }
}
