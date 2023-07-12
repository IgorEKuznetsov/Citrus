package services;

import dto.Mark;
import dto.User;

public class UserService {
  private User user = null;
  private Mark mark = null;
  public User getUserWithMark(Integer id) {
     mark = Mark.builder()
        .name("@contains('Course mark')@")
        .score("@isNumber()@")
        .build();
    return user = User.builder()
        .id(id)
        .name("@matches('^[a-zA-Z\\s\\.]*$')@")
        .course("@matches('^[a-zA-Z\\s]*$')@")
        .email("@contains('@qa.ru')@")
        .age("@isNumber()@")
        .mark(mark)
        .build();
  }
}
