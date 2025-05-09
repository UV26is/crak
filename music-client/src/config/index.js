// 开发环境配置
const dev = {
  baseURL: 'http://localhost:8888',
  resourcePath: '/ziyuanwenjian'
}

// 生产环境配置
const prod = {
  baseURL: 'http://your-production-domain.com',
  resourcePath: '/ziyuanwenjian'
}

// 根据环境变量选择配置
export const baseURL = process.env.NODE_ENV === 'production' ? prod.baseURL : dev.baseURL
export const resourcePath = process.env.NODE_ENV === 'production' ? prod.resourcePath : dev.resourcePath 