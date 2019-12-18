import request from '@/utils/request';

export async function fakeAccountLogin(params) {
  return request('/foodorder/user/login', {
    method: 'POST',
    data: params,
    body: {
      ...params,
      method: 'post'
    }
  });
}
export async function getFakeCaptcha(mobile) {
  return request(`/foodorder/user/login/captcha?mobile=${mobile}`);
}
