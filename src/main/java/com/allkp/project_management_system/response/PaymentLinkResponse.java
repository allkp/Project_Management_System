package com.allkp.project_management_system.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkResponse {

    private String payment_Link_URL;

    private String payment_Link_ID;

}
