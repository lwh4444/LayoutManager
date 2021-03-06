package com.rt.layoutmanager;


import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;


import androidx.fragment.app.FragmentActivity;

import com.rt.mylibrary.DealView;
import com.rt.mylibrary.utils.TestUtils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends FragmentActivity {

    private String testJson = "{" +
            "    \"img\":\"\"," +
            "    \"desc\":\"\"," +
            "    \"name\":\"\"," +
            "    \"loading\":1," +
            "    \"set\":[" +
            "    ]," +
            "    \"type\":\"pc\"," +
            "    \"mp3\":{" +
            "        \"name\":\"\"," +
            "        \"url\":\"\"" +
            "    }," +
            "    \"slider\":{" +
            "        \"animate\":1," +
            "        \"lock\":false," +
            "        \"autoplay\":false," +
            "        \"time\":5" +
            "    }," +
            "    \"style\":{" +
            "        \"width\":1200," +
            "        \"height\":600" +
            "    }," +
            "    \"pages\":[" +
            "        {" +
            "            \"id\":null," +
            "            \"name\":\"\"," +
            "            \"style\":{" +
            "                \"height\":720," +
            "                \"width\":1280," +
            "                \"size\":\"1280X720\"," +
            "                \"direction\":\"horizontal\"," +
            "                \"backgroundColor\":\"rgba(17,68,234,0.84)\"," +
            "                \"backgroundRepeat\":\"no-repeat\"," +
            "                \"backgroundImageCrop\":null," +
            "                \"backgroundSize\":\"100% 100%\"," +
            "                \"backgroundImage\":\"\"" +
            "            }," +
            "            \"layers\":[" +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"音乐组件\"," +
            "                    \"type\":\"music\"," +
            "                    \"icon\":\"iconfont icon-yinle\"," +
            "                    \"data\":{" +
            "                        \"src\":\"http://audio04.dmhmusic.com/71_53_T10046047408_128_4_1_0_sdk-cpm/cn/0206/M00/6C/29/ChR461sZC8yAIP3WADyCYOc_4Yc320.mp3?xcode=59888dc78c11378d626b27d9cf811a77dc49872\"," +
            "                        \"name\":\"Robinson\"," +
            "                        \"delay\":0," +
            "                        \"id\":18" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":0," +
            "                        \"height\":0," +
            "                        \"left\":0," +
            "                        \"top\":0," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"天气组件\"," +
            "                    \"type\":\"weather\"," +
            "                    \"icon\":\"iconfont icon-tianqi\"," +
            "                    \"data\":{" +
            "                        \"area\":[" +
            "                            \"北京市\"," +
            "                            \"东城区\"]," +
            "                        \"style\":{" +
            "                            \"color\":\"rgba(0,0,0,1)\"," +
            "                            \"fontSize\":16" +
            "                        }," +
            "                        \"originalWidth\":250," +
            "                        \"originalHeight\":120" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":364," +
            "                        \"height\":182," +
            "                        \"left\":187," +
            "                        \"top\":87," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"跑马灯组件\"," +
            "                    \"type\":\"run\"," +
            "                    \"icon\":\"iconfont icon-paomadeng-01\"," +
            "                    \"data\":{" +
            "                        \"text\":\"这是跑马灯文字\"," +
            "                        \"direction\":\"left\"," +
            "                        \"style\":{" +
            "                            \"letterSpacing\":0," +
            "                            \"color\":\"rgba(0,0,0,1)\"," +
            "                            \"fontSize\":16," +
            "                            \"lineHeight\":1.5," +
            "                            \"fontWeight\":\"\"," +
            "                            \"fontStyle\":\"\"" +
            "                        }," +
            "                        \"originalWidth\":300," +
            "                        \"originalHeight\":20" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":174," +
            "                        \"height\":24," +
            "                        \"left\":15," +
            "                        \"top\":40," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"图片组件\"," +
            "                    \"type\":\"img\"," +
            "                    \"icon\":\"iconfont icon-tupian\"," +
            "                    \"data\":{" +
            "                        \"id\":27," +
            "                        \"path\":\"data:image/png;base64,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\"," +
            "                        \"name\":\"Perez\"," +
            "                        \"src\":\"data:image/png;base64,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\"," +
            "                        \"originalWidth\":200," +
            "                        \"originalHeight\":100" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":200," +
            "                        \"height\":100," +
            "                        \"left\":798," +
            "                        \"top\":51," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"视频区组件\"," +
            "                    \"type\":\"video\"," +
            "                    \"icon\":\"iconfont icon-shipin\"," +
            "                    \"data\":{" +
            "                        \"playId\":464," +
            "                        \"playName\":\"Thompson\"," +
            "                        \"playItemList\":[" +
            "                            {" +
            "                                \"item_id\":465," +
            "                                \"type\":2," +
            "                                \"timer\":516," +
            "                                \"name\":\"王强\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":466," +
            "                                \"type\":2," +
            "                                \"timer\":419," +
            "                                \"name\":\"石明\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":467," +
            "                                \"type\":2," +
            "                                \"timer\":412," +
            "                                \"name\":\"潘娜\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":468," +
            "                                \"type\":3," +
            "                                \"timer\":725," +
            "                                \"name\":\"邹秀兰\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":469," +
            "                                \"type\":1," +
            "                                \"timer\":375," +
            "                                \"name\":\"曹磊\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":470," +
            "                                \"type\":2," +
            "                                \"timer\":679," +
            "                                \"name\":\"易洋\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":471," +
            "                                \"type\":1," +
            "                                \"timer\":219," +
            "                                \"name\":\"龚洋\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":472," +
            "                                \"type\":2," +
            "                                \"timer\":238," +
            "                                \"name\":\"顾杰\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":473," +
            "                                \"type\":1," +
            "                                \"timer\":794," +
            "                                \"name\":\"傅艳\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":474," +
            "                                \"type\":2," +
            "                                \"timer\":339," +
            "                                \"name\":\"夏芳\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":475," +
            "                                \"type\":2," +
            "                                \"timer\":330," +
            "                                \"name\":\"谢芳\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":476," +
            "                                \"type\":3," +
            "                                \"timer\":564," +
            "                                \"name\":\"汪勇\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":477," +
            "                                \"type\":1," +
            "                                \"timer\":486," +
            "                                \"name\":\"周磊\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":478," +
            "                                \"type\":2," +
            "                                \"timer\":578," +
            "                                \"name\":\"陆娜\"" +
            "                            }," +
            "                            {" +
            "                                \"item_id\":479," +
            "                                \"type\":2," +
            "                                \"timer\":245," +
            "                                \"name\":\"陈军\"" +
            "                            }]," +
            "                        \"originalWidth\":100," +
            "                        \"originalHeight\":50," +
            "                        \"backgroundImage\":\"data:image/png;base64,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\"," +
            "                        \"border\":14" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":328," +
            "                        \"height\":218," +
            "                        \"left\":197," +
            "                        \"top\":323," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"二维码组件\"," +
            "                    \"type\":\"qrcode\"," +
            "                    \"icon\":\"iconfont icon-tianqi\"," +
            "                    \"data\":{" +
            "                        \"url\":\"http://www.baidu.com\"," +
            "                        \"originalWidth\":150," +
            "                        \"originalHeight\":150" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":150," +
            "                        \"height\":150," +
            "                        \"left\":653," +
            "                        \"top\":169," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"按钮组件\"," +
            "                    \"type\":\"btn\"," +
            "                    \"icon\":\"iconfont icon-anniu\"," +
            "                    \"data\":{" +
            "                        \"text\":\"确定\"," +
            "                        \"jumpType\":1," +
            "                        \"url\":\"www.baidu.com\"," +
            "                        \"page\":-1," +
            "                        \"style\":{" +
            "                            \"letterSpacing\":6," +
            "                            \"color\":\"rgba(0,0,0,1)\"," +
            "                            \"fontSize\":16," +
            "                            \"fontWeight\":\"bold\"," +
            "                            \"fontStyle\":\"italic\"" +
            "                        }," +
            "                        \"originalWidth\":130," +
            "                        \"originalHeight\":27" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"left\":636," +
            "                        \"top\":440," +
            "                        \"width\":141," +
            "                        \"height\":50," +
            "                        \"transform\":\"rotate(39deg)\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"opacity\":1," +
            "                        \"borderWidth\":1," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"borderRadius\":0," +
            "                        \"borderColor\":\"rgba(0,0,0,1)\"," +
            "                        \"backgroundColor\":\"rgba(0,0,0,0)\"" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"文本组件\"," +
            "                    \"type\":\"text\"," +
            "                    \"icon\":\"iconfont icon-wenben\"," +
            "                    \"data\":{" +
            "                        \"text\":\"测试文本框\"," +
            "                        \"style\":{" +
            "                            \"letterSpacing\":0," +
            "                            \"color\":\"rgba(0,0,0,1)\"," +
            "                            \"textDecoration\":\"normal\"," +
            "                            \"textAlign\":\"left\"," +
            "                            \"fontSize\":18," +
            "                            \"lineHeight\":1.5," +
            "                            \"fontWeight\":\"\"," +
            "                            \"fontStyle\":\"\"" +
            "                        }," +
            "                        \"originalWidth\":130," +
            "                        \"originalHeight\":27" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":130," +
            "                        \"height\":27," +
            "                        \"left\":497," +
            "                        \"top\":40," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"音乐组件\"," +
            "                    \"type\":\"music\"," +
            "                    \"icon\":\"iconfont icon-yinle\"," +
            "                    \"data\":{" +
            "                        \"src\":\"http://m10.music.126.net/20200409103617/dafb0bfc1dfb7ec2908d133362a541cd/ymusic/8bfa/b225/d9ca/b62debb149be9b2b79cf35a722de2b11.mp3\"," +
            "                        \"name\":\"Wilson\"," +
            "                        \"delay\":0," +
            "                        \"id\":11" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"width\":0," +
            "                        \"height\":0," +
            "                        \"left\":0," +
            "                        \"top\":0," +
            "                        \"transform\":\"\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"borderRadius\":0," +
            "                        \"borderStyle\":\"none\"," +
            "                        \"opacity\":1" +
            "                    }" +
            "                }," +
            "                {" +
            "                    \"key\":\"\"," +
            "                    \"name\":\"时间组件\"," +
            "                    \"type\":\"time\"," +
            "                    \"icon\":\"iconfont icon-shijian\"," +
            "                    \"data\":{" +
            "                        \"originalWidth\":130," +
            "                        \"originalHeight\":27," +
            "                        \"type\":\"hh时mm分ss秒\"," +
            "                        \"style\":{" +
            "                            \"color\":\"rgba(0,0,0,1)\"," +
            "                            \"fontSize\":20" +
            "                        }" +
            "                    }," +
            "                    \"style\":{" +
            "                        \"left\":867," +
            "                        \"top\":278," +
            "                        \"width\":148," +
            "                        \"height\":152," +
            "                        \"transform\":\"rotate(0deg)\"" +
            "                    }," +
            "                    \"estyle\":{" +
            "                        \"opacity\":1," +
            "                        \"borderWidth\":1," +
            "                        \"borderStyle\":\"solid\"," +
            "                        \"borderRadius\":118," +
            "                        \"borderColor\":\"rgba(223,239,64,1)\"," +
            "                        \"backgroundColor\":\"rgba(221,64,64,0.96)\"" +
            "                    }" +
            "                }]," +
            "            \"slider\":{" +
            "                \"animate\":1," +
            "                \"autoplay\":false," +
            "                \"lock\":false," +
            "                \"time\":5" +
            "            }" +
            "        }]" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup rootView = (ViewGroup) findViewById(R.id.rootView);

        DealView.dealData(this, TestUtils.jsonFormat(testJson), rootView, new ThreadPoolExecutor(2, 6, 5000, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(20)));
    }
}