package com.example.hotel.enums;

public enum BizRegion {
    XiDan("西单"),
    XianLin("仙林"),
    GuLou("鼓楼"),
    XinJie("新街口"),
    GuanQianStreet("观前街"),
    JinJiHu("金鸡湖"),
    WangFuJing("王府井"),
    NanLuoGuXiang("南锣鼓巷"),
    TianAnMen("天安门"),
    SanLiTun("三里屯"),
    JingAnSi("静安寺"),
    WaiTan("外滩"),
    LuJiaZui("陆家嘴"),
    HuaQiangBei("华强北"),
    SanGuoCheng("三国城"),
    LingShanDaFo("灵山大佛"),
    YueLuShuYuan("岳麓书院"),
    TianXinGe("天心阁"),
    GuangZhouTa("广州塔");

    private final String value;

    BizRegion(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
