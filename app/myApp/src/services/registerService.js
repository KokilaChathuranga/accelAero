import request from '@/utils/request';

export async function queryTotal(params) {
  return request('/aeroinventory/sampleservice/quoteChargeByPost', {
    method: 'POST',
    body: {
      ...params,
      method: 'post'
    }
  });
}
