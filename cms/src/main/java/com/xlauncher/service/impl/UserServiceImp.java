package com.xlauncher.service.impl;

import com.xlauncher.dao.*;
import com.xlauncher.entity.*;
import com.xlauncher.service.UserService;
import com.xlauncher.util.*;
import com.xlauncher.util.userlogin.Jwt;
import com.xlauncher.util.userlogin.SessionManageListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    TodoUtil todoUtil;

    @Autowired
    CheckToken checkToken;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    AssignmentDao assignmentDao;

    @Autowired
    AuthorizationDao authorizationDao;

    @Autowired
    QuestionStatus questionStatus;

    @Autowired
    TodoDao todoDao;

    @Autowired
    MailDao mailDao;

    @Autowired
    AdminDivisionDao divisionDao;

    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    PermissionUtil permissionUtil;
    @Autowired
    DivAndOrgUtil divAndOrgUtil;
    @Autowired
    private OperationLogUtil logUtil;
    private static final String MODULE = "用户管理";
    private static final String SYSTEM_MODULE = "系统管理";
    private static final String CATEGORY = "运营面";
    private Logger logger = Logger.getLogger(UserServiceImp.class);

    @Override
    public Map<String, Object> login(Map<String,Object> userLogin, HttpServletRequest request
            , HttpServletResponse response,Map<String,Object> responseMap) {
        User user = userDao.getUserByLoginName( (String) userLogin.get("userLoginName"));
        /**角色编号表，利用用户编号从用户角色映射表中获取该用户所有的角色编号*/
        if(user!=null && MD5Util.getResult(String.valueOf(userLogin.get("userPassword"))).equals(user.getUserPassword())) {
            String token = Jwt.sign(responseMap, 12 * 60 * 60 * 1000L);
            if (token != null) {
                user.setToken(token);
            }
            User userIsLogin = new User();
            userIsLogin.setUserLoginName(user.getUserLoginName());
            Boolean hasLogin = SessionManageListener.checkIfHasLogin(user.getUserLoginName());
            if (user.getUserId() == 1 && user.getUserQuestion() == 2) {
                SessionManageListener.removeUserSession(user.getUserLoginName());
                SessionManageListener.removeSession(request.getSession().getId());
                hasLogin = false;
                logger.info("初始化超级管理员用户不记录session！不判断是否已经登录.");
            }
            if (user.getUserId() == 1 && user.getUserQuestion() == -1) {
                SessionManageListener.removeUserSession(user.getUserLoginName());
                SessionManageListener.removeSession(request.getSession().getId());
                hasLogin = false;
                logger.info("初始化超级管理员用户不记录session！不判断是否已经登录.");
            }
            if (hasLogin) {
                responseMap.put("202", "The current user is logged in! Continue to log in?");
                response.setStatus(202);
                String confirm = request.getHeader("continue");
                String eventTrue = "true";
                if (confirm != null && confirm.equals(eventTrue)) {
                    if (SessionManageListener.containsKey(request.getSession().getId())) {
                        responseMap.put("406", "A browser cannot log in multiple users at once!");
                        response.setStatus(406);
                        return responseMap;
                    }
                    SessionManageListener.removeUserSession(user.getUserLoginName());
                    SessionManageListener.removeSession(request.getSession().getId());
                    request.getSession().setAttribute("user", userIsLogin);
                    SessionManageListener.addUserSession(request.getSession());
                    logUtil.opLog(user.getUserName(), "维护", MODULE, SYSTEM_MODULE, "登录并进入未来之瞳-天宫系统", CATEGORY);

                    responseMap.put("mail", mailDao.unread(user.getUserLoginName()));
                    responseMap.put("permission", permissionUtil.getPermission(user.getUserId()));
                    responseMap.put("question", user.getUserQuestion());
                    responseMap.put("userId", user.getUserId().toString());
                    Todo todo = todoDao.getStatusByUserId(user.getUserId());
                    if (todo != null) {
                        responseMap.put("todoStatus", todo.getTodoStatus());
                    } else {
                        responseMap.put("todoStatus", 1);
                    }
                    responseMap.put("token", user.getToken());
                    user.setUserLoginCount(user.getUserLoginCount() + 1);
                    user.setUserLastLogin(DatetimeUtil.getDate(System.currentTimeMillis()));
                    if (user.getUserQuestion() != 1) {
                        user.setUserQuestion(-2);
                    }
                    user.setUserStatus(1);
                    userDao.updateUser(user);
                } else {
                    if (user.getUserQuestion() == 2) {
                        user.setUserQuestion(2);
                    }
                    responseMap.put("question", user.getUserQuestion());
                    return responseMap;
                }
            } else {
                if (SessionManageListener.containsKey(request.getSession().getId())) {
                    responseMap.put("406", "A browser cannot log in multiple users at once!");
                    response.setStatus(406);
                    return responseMap;
                } else {
                    request.getSession().setAttribute("user", userIsLogin);
                    SessionManageListener.addUserSession(request.getSession());
                    logger.info(userLogin.get("userLoginName") + " The current user login successful!");
                    logUtil.opLog(user.getUserName(), "维护", MODULE, SYSTEM_MODULE, "登录并进入未来之瞳-天宫系统", CATEGORY);

                    user.setUserLoginCount(user.getUserLoginCount() + 1);
                    user.setUserLastLogin(DatetimeUtil.getDate(System.currentTimeMillis()));
                    if (user.getUserQuestion() == 2) {
                        user.setUserQuestion(2);
                    } else if (user.getUserQuestion() != 1) {
                        user.setUserQuestion(-2);
                    }
                    user.setUserStatus(1);
                    userDao.updateUser(user);
                    responseMap.put("mail", mailDao.unread(user.getUserLoginName()));
                    responseMap.put("permission", permissionUtil.getPermission(user.getUserId()));
                    responseMap.put("question", user.getUserQuestion());
                    responseMap.put("userId", user.getUserId().toString());
                    Todo todo = todoDao.getStatusByUserId(user.getUserId());
                    if (todo != null) {
                        responseMap.put("todoStatus", todo.getTodoStatus());
                    } else {
                        responseMap.put("todoStatus", 1);
                    }
                    responseMap.put("token", user.getToken());
                    responseMap.put("201", userLogin.get("userLoginName") + " The current user login successful!");
                    response.setStatus(201);
                }
            }

        } else {
            responseMap.put("401","Input is not correct! Please log in again!!!!!");
            response.setStatus(401);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> userInfo(String token) {
        Map<String,Object> map = new HashMap<>(1);
        User user =  userDao.checkUser(token);
        logger.info("用户个人信息查看userInfo：" + user);
        if(user!=null) {String role = "";
            List<Integer> roleNameList = assignmentDao.listAssign(user.getUserId());
            if (roleNameList.size()!=0){
                for (int roleId:
                        roleNameList) {
                    role=role+roleDao.getRoleNameById(roleId)+",";
                }
            }
            if(role.length()!=0) {
                user.setUserRole(role.substring(0, role.length() - 1));
            }
            List<Question> questionList = questionDao.listQuestion(user.getUserId());
            List<Integer> roleIdList = assignmentDao.listAssign(user.getUserId());
            List<Role> roleList = new ArrayList<>();
            if(roleIdList.size()!=0){
                for (int roleId:
                        roleIdList) {
                    roleList.add(roleDao.getRoleById(roleId));
                }
            }
            map.put("userInfo",user);
            map.put("userQuestion",questionList);
            map.put("userRole",roleList);
        }else {
            User user1 = this.userDao.checkInitUser(token);
            logger.info("未分配组织和区划的用户查看个人信息userInfo：" + user1);
            List<Question> questionList = questionDao.listQuestion(user1.getUserId());
            map.put("userInfo",user1);
            map.put("userQuestion",questionList);
        }
        return map;
    }

    /**
     * 提供发送邮件的用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> mailUser(String token, String userName) {
        Map<String, Object> map = new HashMap<>(1);
        List<User> user = this.userDao.mailUser(userName);
        map.put("data",user);
        return map;
    }

    /**
     * 查看用户详情
     * @param userId
     * @param token
     * @return
     */
    @Override
    public User getUserById(int userId,String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询编号为：" + userId + "的用户信息",CATEGORY);
        User user = userDao.getUserById(userId);
        logger.info("查看用户详情getUserById：" + user);
        String role = "";
        List<Integer> roleList = assignmentDao.listAssign(userId);
        if (roleList.size()!=0){
            for (int roleId:
                    roleList) {
                role=role+roleDao.getRoleNameById(roleId)+",";
            }
        }
        if(role.length()!=0) {
            user.setUserRole(role.substring(0, role.length() - 1));
        }
        user.setOrgName(divAndOrgUtil.orgName(user.getUserOrgId()));
        user.setDivisionName(divAndOrgUtil.divisionName(user.getAdminDivisionId()));
        return user;
    }

    /**
     * 用户退出系统将用户状态置为-1
     *
     * @param userLoginName 用户名
     * @return 模糊查询结果
     */
    @Override
    public void getUserByLoginName(String userLoginName) {
        logger.info("用户退出系统将用户状态置为不在线getUserByLoginName:" + userLoginName);
        User user = this.userDao.getUserByLoginName(userLoginName);
        user.setUserStatus(-1);
        this.userDao.updateUser(user);
    }

    /**
     * 查询所有用户
     * @param userName 用户姓名
     * @param roleName 用户角色
     * @param userCode 用户编号
     * @param number   页码
     * @param token    令牌
     * @return
     */
    @Override
    public List<User> listUser(String userName, String roleName, String userCode, int number, String token) {
        logger.info("查询所有用户listUser");
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据索引条件：用户姓名, 角色名称, 用户编号, 查询用户信息",CATEGORY);
        List<User> users = userDao.listUser(userName,userCode,(number-1)*10);
        if (users.size()!=0){
            // 类似于迭代器
            users.removeIf(nowUser -> Objects.equals(nowUser.getUserId(), checkToken.checkToken(token).getUserId()));
            for (User user:
                    users) {
                String role = "";
                List<Integer> roleList = assignmentDao.listAssign(user.getUserId());
                if (roleList.size()!=0){
                    for (int roleId:
                            roleList) {
                        role=role+roleDao.getRoleNameById(roleId)+",";
                    }
                }
                if(role.length()!=0) {
                    user.setUserRole(role.substring(0, role.length() - 1));
                }
            }
        }
        return users;
    }

    @Override
    public int countPage(String userName, String roleName, String userCode, String token) {
        return userDao.countPage(userName,roleName,userCode);
    }

    /**
     * 用户注册
     * @param user 需要添加的用户信息
     * @return
     */
    @Override
    public int register(User user) {
        logger.info("用户注册register:" + user);
        user.setUserStatus(-2);
        user.setUserQuestion(-1);
        // MD5加密算法
        user.setUserPassword(MD5Util.getResult(String.valueOf(user.getUserPassword())));
        int status = userDao.insertUser(user);
        todoUtil.registerTodo(user.getUserId());
        return status;
    }

    /**
     * 用户注册信息
     * @param todoId 待办编号
     * @param token  令牌
     * @return
     */
    @Override
    public User registerInfo(int todoId, String token) {
        logger.info("用户注册信息registerInfo, todoId:" + todoId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"维护",MODULE,SYSTEM_MODULE,"维护待办编号：" + todoId + "的注册信息",CATEGORY);
        return userDao.getToDoUserById(todoDao.getUserById(todoId));
    }

    /**
     * 用户注册确认（系统管理员确认用户注册信息）
     * @param userInfo
     * @param token    令牌
     * @return
     */
    @Override
    public int registerConfirm(Map<String, Object> userInfo, String token) {
        logger.info("用户注册确认,系统管理员确认用户注册信息registerConfirm:" + userInfo);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"维护",MODULE,SYSTEM_MODULE,"确认用户注册信息" ,CATEGORY);
        User user = new User();
        user.setUserId(Integer.parseInt(userInfo.get("userId").toString()));
        user.setUserCode((String)userInfo.get("userCode"));
        BigInteger bi = new BigInteger(String.valueOf(userInfo.get("adminDivisionId")));
        user.setAdminDivisionId(bi);
        user.setUserOrgId((String)userInfo.get("userOrgId"));
        user.setUserStatus(-1);
        todoDao.agreeTodo(Integer.parseInt((String)userInfo.get("todoId")),checkToken.checkToken(token).getUserName());
        int status = userDao.updateUser(user);
        List<String> roleList = (List<String>) userInfo.get("assign");
        if(!(roleList.containsAll(assignmentDao.listAssign(user.getUserId()))&&assignmentDao.listAssign(user.getUserId()).containsAll(roleList))){
            assignmentDao.deleteAssignByUser(user.getUserId());
            for (String role:
                    roleList) {
                assignmentDao.insertAssign(user.getUserId(),Integer.parseInt(role));
            }
        }
        return status;
    }

    /**
     * 用户登录名校验
     *
     * @param userLoginName 用户登录名
     * @return
     */
    @Override
    public int countUserByLoginName(String userLoginName) {
        logger.info("用户登录名校验userLoginName:" + userLoginName);
        return userDao.countUserByLoginName(userLoginName);
    }

    /**
     * 用户证件信息查重
     *
     * @param userCardId 用户证件信息
     * @return 已经存在返回1，未存在返回0
     */
    @Override
    public int countUserCardId(String userCardId) {
        logger.info("用户证件信息查重校验userCardId:" + userCardId);
        return userDao.countUserCardId(userCardId);
    }

    /**
     * 获取用户编号用于前端校验
     *
     * @param userCode 用户编号
     * @param userId
     * @return 用户编号
     */
    @Override
    public int countUserCode(String userCode, String userId) {
        logger.info("用户编号查重校验userCode:" + userCode + ",userId:" + userId);
        return this.userDao.countUserCode(userCode,userId);
    }

    /**
     * 添加用户
     * @param userInfo 需要添加的用户信息
     * @param token    令牌
     * @return
     */
    @Override
    public int insertUser(Map<String,Object> userInfo, String token) {
        logger.info("添加用户insertUser:" + userInfo);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加用户信息" ,CATEGORY);
        int status;
        User user = new User();
        user.setUserLoginName((String)userInfo.get("userLoginName"));
        user.setUserCode((String)userInfo.get("userCode"));
        user.setUserName((String)userInfo.get("userName"));
        // MD5加密
        user.setUserPassword(MD5Util.getResult(String.valueOf(userInfo.get("userPassword"))));
        user.setUserCardId((String)userInfo.get("userCardId"));
        user.setUserPhone((String)userInfo.get("userPhone"));
        user.setUserEmail((String)userInfo.get("userEmail"));
        BigInteger bi = new BigInteger(String.valueOf(userInfo.get("adminDivisionId")));
        user.setAdminDivisionId(bi);
        user.setUserOrgId((String)userInfo.get("userOrgId"));
        user.setUserStatus(0);
        status = userDao.insertUser(user);
        /**获取用户的编号*/
        List<String> roleList =(List<String>) userInfo.get("roleList");
        for (String roleId:
                roleList) {
            assignmentDao.insertAssign(user.getUserId(),Integer.parseInt(roleId));
        }
        return status;
    }

    /**
     * 更新用户信息
     * @param userInfo
     * @param token    令牌
     * @return
     */
    @Override
    public int updateUser(Map<String,Object> userInfo, String token) {
        logger.info("更新用户信息updateUser:" + userInfo);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"员工编号：" + checkToken.checkToken(token).getUserCode() + ", 更新用户信息userInfo:" + userInfo ,CATEGORY);
        User user = new User();
        user.setUserId(Integer.parseInt(userInfo.get("userId").toString()));
        user.setUserLoginName((String)userInfo.get("userLoginName"));
        user.setUserCode((String)userInfo.get("userCode"));
        user.setUserName((String)userInfo.get("userName"));
        if (userInfo.get("userPassword") != null) {
            // MD5加密
            user.setUserPassword(MD5Util.getResult(String.valueOf(userInfo.get("userPassword"))));
        }
        user.setUserCardId((String)userInfo.get("userCardId"));
        user.setUserPhone((String)userInfo.get("userPhone"));
        user.setUserEmail((String)userInfo.get("userEmail"));
        if (userInfo.get("adminDivisionId") != null) {
            BigInteger bi = new BigInteger(String.valueOf(userInfo.get("adminDivisionId")));
            user.setAdminDivisionId(bi);
        }
        if (userInfo.get("userOrgId") != null) {
            user.setUserOrgId(userInfo.get("userOrgId").toString());
        }
        int status = userDao.updateUser(user);
        if (userInfo.get("assign")!=null) {
            List<String> roleList = (List<String>) userInfo.get("assign");
            if (!(roleList.containsAll(assignmentDao.listAssign(user.getUserId())) && assignmentDao.listAssign(user.getUserId()).containsAll(roleList))) {
                assignmentDao.deleteAssignByUser(user.getUserId());
                for (String role :
                        roleList) {
                    assignmentDao.insertAssign(user.getUserId(), Integer.parseInt(role));
                }
            }
        }
        return status;
    }

    /**
     * 修改密码
     * @param userInfo
     * @return
     */
    @Override
    public int updatePassword(Map<String, Object> userInfo) {
        logger.info("修改密码updatePassword:" + userInfo);
        User user = new User();
        user.setUserId(Integer.parseInt((String) userInfo.get("userId")));
        // MD5加密
        user.setUserPassword(MD5Util.getResult(String.valueOf(userInfo.get("userPassword"))));
        if (user.getUserId() == 1) {
            // 初始化超级管理员修改密码后，question置为1
            user.setUserQuestion(-1);
        }
        return userDao.updateUser(user);
    }

    @Override
    public List<Integer> assignList(int userId,String token) {
        return assignmentDao.listAssign(userId);
    }

    /**
     * 删除用户
     * @param userId 用户编号
     * @param token  令牌
     * @return
     */
    @Override
    public int deleteUser(int userId,String token) {
        logger.info("删除用户并销毁相关信息deleteUser, userId:" + userId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除用户编号：" + userId + "的用户信息",CATEGORY);
        // 清除session信息
        SessionManageListener.removeUserSession(checkToken.checkToken(token).getUserLoginName());
        todoDao.deleteUserTodo(userId);
        assignmentDao.deleteAssignByUser(userId);
        return userDao.deleteUser(userId);
    }

    /**
     * 添加密保问题
     * @param questionList
     * @param token        令牌
     * @return
     */
    @Override
    public int insertQuestion(List<Question> questionList, String token) {
        logger.info("添加密保问题insertQuestion:" + questionList);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加密保问题信息",CATEGORY);
        Integer userId = questionList.get(0).getQuestionUserId();
        User userQuestion = userDao.getToDoUserById(userId);
        if (userQuestion.getUserQuestion() == -2) {
            for (Question question :
                    questionList) {
                questionDao.insertQuestion(question);
            }
            questionStatus.setStatusPositive(questionList.get(0).getQuestionUserId());
        } else {
            return -1;
        }
        return 1;
    }

    /**
     * 删除密保问题
     * @param userId 密保问题对应的编号
     * @param token  令牌
     * @return
     */
    @Override
    public int deleteQuestionByUser(int userId, String token) {
        logger.info("删除密保问题deleteQuestionByUser, userId:" + userId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除密保问题信息",CATEGORY);
        questionStatus.setStatusNegative(userId);
        return questionDao.deleteQuestionByUser(userId);
    }

    /**
     * 修改密保问题
     * @param questionList
     * @param token 令牌
     * @return
     */
    @Override
    public int updateQuestion(List<Question> questionList, String token) {
        logger.info("修改密保问题updateQuestion:" + questionList);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"更新密保问题信息",CATEGORY);
        for (Question question:
                questionList) {
            questionDao.updateQuestion(question);
        }
        return 1;
    }

    @Override
    public List<Question> listQuestion(int userId) {
        return  questionDao.listQuestion(userId);

    }

    /**
     * @param question
     * @return
     */
    @Override
    public int questionCheck(List question) {
        logger.info("questionCheck:" + question);
        Map<String,Object> map1 = (Map<String, Object>) question.get(0);
        Map<String,Object> map2 = (Map<String, Object>) question.get(1);
        Map<String,Object> map3 = (Map<String, Object>) question.get(2);
        if ((questionDao.getQuestionById(Integer.parseInt((String)map1.get("questionId"))).getQuestionAnswer().equals(map1.get("questionAnswer")))
                &&(questionDao.getQuestionById(Integer.parseInt((String)map2.get("questionId"))).getQuestionAnswer().equals(map2.get("questionAnswer")))
                &&(questionDao.getQuestionById(Integer.parseInt((String)map3.get("questionId"))).getQuestionAnswer().equals(map3.get("questionAnswer")))){
            return 1;
        }
        return 0;
    }

    @Override
    public int infoCheck(Map<String, Object> userInfo) {
        logger.info("infoCheck:" + userInfo);
        User user = userDao.userCheck((String)userInfo.get("userLoginName"),(String)userInfo.get("userName"),(String)userInfo.get("userCardId"),(String)userInfo.get("userPhone"));
        if (user!=null){
            todoUtil.resetCodeTodo(user.getUserId());
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int checkInfo(Map<String, Object> userInfo) {
        logger.info("checkInfo:" + userInfo);
        User user = userDao.userCheck((String)userInfo.get("userLoginName"),(String)userInfo.get("userName"),(String)userInfo.get("userCardId"),(String)userInfo.get("userPhone"));
        if(user!=null){
            return user.getUserId();
        }
        else {
            return -1;
        }
    }

    /**
     * token过期 销毁
     *
     * @param token
     * @return
     * @throws SQLException
     */
    @Override
    public int deleteToken(String token) throws SQLException {
        logger.info("token过期销毁deleteToken");
        return userDao.deleteToken(token);
    }

    /**
     * 根据userLoginName获得token
     *
     * @param userForCheck
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    @Override
    public void getToken(Map<String, String> userForCheck, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        logger.info("根据userLoginName获得token:" + userForCheck);
        String sendToken = userForCheck.get("token");
        String sendUserLoginName = userForCheck.get("userLoginName");
        String dbToken = userDao.getToken(sendUserLoginName);
        User unSign = Jwt.unSign(sendToken, User.class);
        /**
         * 验证sendToken
         */
        if (unSign != null){
            /**
             * sendToken和dbToken对比
             */
            if (sendToken.equals(dbToken)) {

                if (request.getSession(false) != null) {
                    /**
                     * session没有过期
                     */
                    response.setStatus(201);
                }
                else {
                    /**
                     * session过期
                     */
                    userDao.deleteToken(sendToken);
                    response.setStatus(400);
                }
            }
            else {
                /**
                 * 请求的token和数据库中的token不一致
                 */
                response.setStatus(400);
            }
        } else {
            /**
             * sendToken过期
             */
            logger.warn(sendUserLoginName + "Token is invalid!");
            userDao.deleteToken(sendToken);
            response.setStatus(400);
        }
    }
}
