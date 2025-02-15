import axios from 'axios';
import FileSaver from 'file-saver';
import errorCode from '@/utils/errorCode';
import { blobValidate } from '@/utils/ruoyi';
import { LoadingInstance } from 'element-plus/es/components/loading/src/loading';
import { globalHeaders } from '@/utils/request';

const baseURL = import.meta.env.VITE_APP_BASE_API;
let downloadLoadingInstance: LoadingInstance;
export default {
  async download(url: string) {
    console.log('download >> ', url)
    downloadLoadingInstance = ElLoading.service({ text: '正在下载数据，请稍候', background: 'rgba(0, 0, 0, 0.7)' });
    try {
      const headers = {
        "Access-Control-Allow-Origin" : "*",
        "Access-Control-Allow-Headers": "content-type,Content-Disposition",
        "Access-Control-Allow-Methods": "DELETE,PUT,POST,GET,OPTIONS",
        ...globalHeaders()
      }

      const res = await axios({
        method: 'get',
        url: url,
        responseType: 'blob',
        headers: headers
      });
      const isBlob = blobValidate(res.data);
      if (isBlob) {
        const blob = new Blob([res.data], { type: 'application/octet-stream' });
        // 获取文件名从响应头
        const contentDisposition = res.headers['Content-Disposition'] || res.headers['content-disposition'];
        let filename = 'unknown_file';
        if (contentDisposition && contentDisposition.includes('filename=')) {
          filename = contentDisposition.split('filename=')[1].split(';')[0].trim();
          filename = decodeURIComponent(filename);
        } else if (res.headers['filename']) {
          filename = decodeURIComponent(res.headers['download-filename'] as string);
        }
        console.log("headers = ", JSON.stringify(res.headers))
        FileSaver.saveAs(blob, filename);
      } else {
        this.printErrMsg(res.data);
      }
      downloadLoadingInstance.close();
    } catch (r) {
      console.error(r);
      ElMessage.error('下载文件出现错误，请联系管理员！');
      downloadLoadingInstance.close();
    }
  },
  async oss(ossId: string | number) {
    const url = baseURL + '/resource/oss/download/' + ossId;
    downloadLoadingInstance = ElLoading.service({ text: '正在下载数据，请稍候', background: 'rgba(0, 0, 0, 0.7)' });
    try {
      const res = await axios({
        method: 'get',
        url: url,
        responseType: 'blob',
        headers: globalHeaders()
      });
      const isBlob = blobValidate(res.data);
      if (isBlob) {
        const blob = new Blob([res.data], { type: 'application/octet-stream' });
        FileSaver.saveAs(blob, decodeURIComponent(res.headers['download-filename'] as string));
      } else {
        this.printErrMsg(res.data);
      }
      downloadLoadingInstance.close();
    } catch (r) {
      console.error(r);
      ElMessage.error('下载文件出现错误，请联系管理员！');
      downloadLoadingInstance.close();
    }
  },
  async zip(url: string, name: string) {
    url = baseURL + url;
    downloadLoadingInstance = ElLoading.service({ text: '正在下载数据，请稍候', background: 'rgba(0, 0, 0, 0.7)' });
    try {
      const res = await axios({
        method: 'get',
        url: url,
        responseType: 'blob',
        headers: globalHeaders()
      });
      const isBlob = blobValidate(res.data);
      if (isBlob) {
        const blob = new Blob([res.data], { type: 'application/zip' });
        FileSaver.saveAs(blob, name);
      } else {
        this.printErrMsg(res.data);
      }
      downloadLoadingInstance.close();
    } catch (r) {
      console.error(r);
      ElMessage.error('下载文件出现错误，请联系管理员！');
      downloadLoadingInstance.close();
    }
  },
  async printErrMsg(data: any) {
    const resText = await data.text();
    const rspObj = JSON.parse(resText);
    const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default'];
    ElMessage.error(errMsg);
  }
};
