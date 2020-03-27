import com.zhaolei.material.admin.dao.graduation.UserMapper;
import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.service.OrganizationService;
import com.zhaolei.material.admin.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations={"classpath*:/spring/application.xml"}) //加载配置文件
public class AdminTest {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){
        OrganizationDO organizationDO1 = new OrganizationDO(5,"电子信息工程","赵磊","222016333210145","13060219411","电子信息工程","token",new Date(),new Date(),0);
        organizationDO1.setOrgName(null);
        OrganizationDO organizationDO2 = new OrganizationDO();
        organizationDO2.setOrgName("电子信息工程");
        BeanUtils.copyProperties(organizationDO1,organizationDO2,getNullPropertyNames(organizationDO2));
        System.out.println();
        System.out.println(organizationDO2);
    }
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[emptyNames.size()]);
    }
}
