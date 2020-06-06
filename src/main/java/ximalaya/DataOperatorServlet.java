//package ximalaya;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ximalaya.southgate.admin.service.impl.AppService;
//import com.ximalaya.southgate.common.util.SouthgateSpringContextUtil;
//import com.ximalaya.southgate.common.util.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Slf4j
//public class DataOperatorServlet extends HttpServlet {
//
//    private AppService appService;
//
//    @Override
//    public void init() {
//        appService = (AppService) SouthgateSpringContextUtil.getBean("appService");
//    }
//
//    @Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        service(req, resp);
//    }
//
//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        service(req, resp);
//    }
//
//    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        try {
//            InputStream is = req.getInputStream();
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            byte[] bytes = new byte[1024];
//            int length = -1;
//            while ((length = is.read(bytes)) > 0) {
//                outputStream.write(bytes, 0, length);
//            }
//            byte[] content = outputStream.toByteArray();
//
//            if (content.length <= 0) {
//                resp.setStatus(400);
//                resp.getWriter().write("content is empty!");
//                log.warn("content is empty!");
//                return;
//            }
//
//            try {
//                final JSONObject jsonObject = JSONObject.parseObject(new String(content, "UTF-8"));
//                String type = jsonObject.getString("type");
//                if (StringUtil.isEmptyString(type)) {
//                    resp.setStatus(400);
//                    resp.getWriter().write("type is empty!");
//                    log.warn("type is empty!");
//                    return;
//                }
//
//                if ("".equals(type)) {
//                    String appId = req.getParameter("appId");
//                    String appStatus = req.getParameter("appStatus");
//                    if (StringUtil.isEmptyString(appId) || StringUtil.isEmptyString(appStatus)) {
//                        resp.setStatus(400);
//                        resp.getWriter().write(String.format("appId or appStatus is empty, appId:%s, appStatus:%s", appId, appStatus));
//                        return;
//                    }
//                    flushAppStatus(Long.parseLong(appId), Integer.parseInt(appStatus));
//                }
//
//                resp.setStatus(200);
//                resp.getWriter().write("ok");
//            } catch (Throwable e) {
//                log.error("Admin data operator parse json failed:", e);
//            }
//        } catch (IOException e) {
//            log.error("admin read southgate data operator failed:", e);
//            resp.setStatus(500);
//            resp.getWriter().write("failed");
//        } finally {
//            resp.getWriter().flush();
////      LOG.warn("service ended");
//        }
//    }
//
//    /**
//     * 更新app的状态
//     */
//    private void flushAppStatus(long appId, int appStatus) {
//        appService.updateState(appId, appStatus);
//    }
//
//}
