package phoenix.user.service;

import phoenix.user.entity.User;

public interface UserService {

    User findByUserName(String userName);
}