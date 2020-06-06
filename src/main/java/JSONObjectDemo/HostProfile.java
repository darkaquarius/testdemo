package JSONObjectDemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostProfile {

    private String hostIp;

    private int port = -1;
}
