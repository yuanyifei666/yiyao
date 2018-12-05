package com.yuan.yiyao.ex;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 全局异常处理
 */

public class MyExceptionHandller implements HandlerExceptionResolver {

    private HttpMessageConverter<MyException> jsonConverter ;

    /*
     * 全局异常处理
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {


        //得到请求的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断该请求返回的是json还是视图
        ResponseBody responseBody = AnnotationUtils.findAnnotation(method,ResponseBody.class);

//        if (responseBody == null){
            //返回逻辑视图
            ModelAndView view = new ModelAndView();
            view.setViewName("error");
            return view;
//        }else{
//            //返回json数据
//
//            ModelAndView view = exceptionMessageToJson(request,response,handler,e);
//            return view;
//        }

    }
    private  MyException initExceptionMessage(Exception exception){

        String resultInfo = "";
        if (exception instanceof MyException) {
            resultInfo = ((MyException) exception).getMessager();
        }else {
            resultInfo = "未知的异常!";
        }
        return new MyException(resultInfo);
    }
    private  ModelAndView exceptionMessageToJson(HttpServletRequest request, HttpServletResponse response, Object handler,
                                                 Exception ex){

        MyException myException = initExceptionMessage(ex);
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
       //把异常转换为json数据输出
        try {
            jsonConverter.write(myException, MediaType.APPLICATION_JSON, outputMessage);
        } catch (HttpMessageNotWritableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView();
    }

    public HttpMessageConverter<MyException> getJsonConverter() {
        return jsonConverter;
    }

    public void setJsonConverter(HttpMessageConverter<MyException> jsonConverter) {
        this.jsonConverter = jsonConverter;
    }


}
