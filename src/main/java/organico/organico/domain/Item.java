package organico.organico.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Item {
    private Long id;            //상품ID
    private String name;        //상품이름
    private String info;        //상품정보
    private int price;            //상품가격
    private String origin;        //상품원산지
    private String ingredient;    //상품성분
    private String unit;        //상품판매개수
    private int quantity;        //상품개수선택

    private String imgName;        // 파일명
    private String imgPath;        // 파일경로

    public Item(String name, int price, String origin) {
        this.name = name;
        this.price = price;
        this.origin = origin;
    }
}