package dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @JsonProperty("userId")
  private Integer id;
  @JsonProperty("email")
  private String email;
  @JsonProperty("name")
  private String name;
  @JsonProperty("course")
  private String course;
  @JsonProperty("age")
  private String age;
  @JsonProperty("mark")
  private Mark mark;
}
