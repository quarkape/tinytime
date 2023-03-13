package club.hue.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostNotify {

    private String id;

    private String notifyid;

    private String title;

    private String detail;

    private int status;

    private String date;

}
