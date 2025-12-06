import axios from "axios";
import { ElMessage,ElLoading } from "element-plus";
// console.log("import.meta.env", import.meta.env.VITE_API_URL);

//创建一个新的请求实例instance,instance.的用法和axios.的用法一致，可以使用instance({})、instance.get（）、instance.post()
const instace = axios.create({
  baseURL: import.meta.env.VITE_API_URL, //默认配置(这里不要用process.env,个人百度这个在vite中被废弃掉了,属性名必须以VITE_API_开头,否则 import.meta.env检测不到)
  timeout: 5000, //超时时间
});

//配置请求拦截器,在请求之前的数据处理,比如在请求头添加token,所有的请求都会经过拦截器
instace.interceptors.request.use(
  //config:该参数表示当前请求的配置对象
  (config) => {
    console.log(import.meta.env.VITE_API_URL);
    //例如:
    //在请求头统一添加token
    //或者请求之前显示lodding图标(这里只演示这个)
    //这里是vant组件库的loadding,安装和配置请查看此文档的vant组件库的配置https://blog.csdn.net/weixin_68658847/article/details/129331162
    // Toast.loading({
    //   duration: 0,
    //   message: "正在努力加载",
    // });
    return config;
  },
  (err) => {
    //Toast.clear(); //请求失败关闭loadding
    return Promise.reject(err); //将错误消息挂到promise的失败函数上
  }
);

//配置响应拦截器
// 响应拦截器:在请求响应之后对数据处理，比如:登录失败、请求数据失败的处理
// instance.interceptors.response.use(response=>{l}, err=>{});
// 响应成功:执行回调函数1;响应失败，执行回调函数2
instace.interceptors.response.use(
  (response) => {
    let {status,data} = response;
    if (status !== 200) {
      ElMessage.error("请求错误")
      return Promise.reject(err);
    }
    let {code,message} = data;
    if (code !== 200) {
      ElMessage.error(message?message:"请求失败")
      return Promise.reject(err);
    }
    //Toast.clear(); //响应成功关闭loadding
    return data.data; //这里的response就是请求成功后的res , response.data即是请求成功后回调函数内的参数res.data
  },
  (err) => {
    //Toast.clear(); //响应失败关闭loadding
    return Promise.reject(err); //将错误消息挂到promise的失败函数上
  }
);

//封装请求的api
const callapi = (method = "GET", url, data = {}) => {
  return instace({
    method,
    url,
    params: method === "GET" ? data : {},
    data: method !== "GET" ? data : {},
  });
};
//封装GET请求函数
export const getapi = (url, data) => callapi("GET", url, data);
export const postapi = (url, data) => callapi("POST", url, data);
export const putapi = (url, data) => callapi("PUT", url, data);
export const delapi = (url, data) => callapi("DELETE", url, data);
