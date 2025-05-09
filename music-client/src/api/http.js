import axios from 'axios';
import router from '../router';
import { baseURL } from '../config/index';

axios.defaults.timeout = 5000;  //超时时间是5秒
//Content-Type 响应头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
//基础url
axios.defaults.baseURL = baseURL;

//响应拦截器
axios.interceptors.response.use(
    response => {
        return Promise.resolve(response);
    },
    error => {
        if (error.response) {
            switch(error.response.status){
                case 401:       //未登录
                    router.replace({
                        path:'/',
                        query:{
                            redirect: router.currentRoute.fullPath
                        }
                    });
                    break;
                case 404:   //没找到
                    break;
            }
            return Promise.reject(error.response);
        } else {
            return Promise.reject(error);
        }
    }
);

/**
 * 封装get方法
 */
export function get(url,params={}){
    return new Promise((resolve,reject) => {
        axios.get(url,{params:params})
        .then(response =>{
            resolve(response.data);
        })
        .catch(err =>{
            reject(err);
        })
    });
}

/**
 * 封装post方法
 */
export function post(url,data={}){
    return new Promise((resolve,reject) => {
        axios.post(url,data)
        .then(response =>{
            resolve(response.data);
        })
        .catch(err =>{
            reject(err);
        })
    });
}