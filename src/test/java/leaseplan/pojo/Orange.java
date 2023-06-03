package leaseplan.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter // These comes from lombok library and autogenerated methods
@ToString

public class Orange {

    // this pojo class allow us to store single orange details in a java custom object

    private String provider;
    private String title;
    private String url;
    private String brand;
    private double price;
    private String unit;
    @JsonProperty("isPromo")
    private boolean isPromo;
    private String promoDetails;
    private String image;






}
