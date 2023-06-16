package az.code.auction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String toEmail;
    private String subject;
    private String text;
}
