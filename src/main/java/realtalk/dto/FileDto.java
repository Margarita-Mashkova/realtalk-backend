package realtalk.dto;

import lombok.Data;

@Data
public class FileDto {
    private String base64;
    private String name;
    private String type;
    private String lastModified;
}
