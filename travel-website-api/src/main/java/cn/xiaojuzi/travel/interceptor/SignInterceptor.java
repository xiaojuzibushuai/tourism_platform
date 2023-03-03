package cn.xiaojuzi.travel.interceptor;

import cn.xiaojuzi.travel.util.JsonResult;
import cn.xiaojuzi.travel.util.Md5Utils;
import com.alibaba.fastjson.JSON;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名拦截(防篡改)
 * author:xiaojuzi
 */
public class SignInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return  true;
        }
        /**
         * {
         *     a:"1"
         *     b:"2"
         *     c:"3"
         *     d:"4"
         * }
         *
         */
        //签名验证
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> param = new HashMap<>();
        for (String s : map.keySet()) {
            if("sign".equalsIgnoreCase(s)){
                continue;
            }
            param.put(s, arrayToString(map.get(s)));
        }
        String signatures = Md5Utils.signatures(param);  //后端： sign_server
        String sign = request.getParameter("sign");//前端： sign_client
        if(sign == null || !sign.equalsIgnoreCase(signatures)){
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(new JsonResult(501,"签名校验失败","不好意思咯")));
            return false;
        }
        return true;
    }


    private String arrayToString(String [] array){

        StringBuilder sb = new StringBuilder(10);
        for (String s : array) {
            sb.append(s);
        }
        return sb.toString();
    }
}