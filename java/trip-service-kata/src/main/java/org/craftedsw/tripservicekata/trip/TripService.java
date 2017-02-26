package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (getLoggedInUser() == null) {
            throw new UserNotLoggedInException();
        }
        return user.isFriendsWith(getLoggedInUser())
                ? tripsBy(user)
                : noTrips();
	}

    private List<Trip> noTrips() {
        return new ArrayList<>();
    }

    List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}

	User getLoggedInUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
