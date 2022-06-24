package twenty2.auth.shared.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleModel {
    private Long id;
    private String description;
}
