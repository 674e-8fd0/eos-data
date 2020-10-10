package io.jafka.jeos.core.common.transaction;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionAuthorization {

    private String actor;
    private String permission;
}
