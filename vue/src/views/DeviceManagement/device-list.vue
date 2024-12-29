<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="data.deviceId" style="width: 260px;margin-right: 10px" placeholder="请输入设备编号查询"
                :prefix-icon="Search"/>
      <el-input v-model="data.deviceName" style="width: 260px;margin-right: 10px" placeholder="请输入设备名称查询"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table :data="data.tableData" style="width: 100%">
        <el-table-column prop="deviceId" label="设备编号"></el-table-column>
        <el-table-column prop="deviceName" label="设备名称"></el-table-column>
        <el-table-column prop="depreciationRate" label="折旧率（元/小时）"></el-table-column>
        <el-table-column prop="electricityRate" label="电费率（元/小时）"></el-table-column>

        <el-table-column label="二维码">
          <template #default="scope">
            <img :src="scope.row.qrCode" style="width: 100px; height: 100px;" />
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:currentPage="data.pageNum"
          v-model:pageSize="data.pageSize"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :page-sizes="[5, 10, 20, 50, 100]"
          layout="sizes, prev, pager, next"
          :total="data.total">
      </el-pagination>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { Search } from "@element-plus/icons-vue";
import request from "@/utils/request";
// 假设 QRCode 库已经通过某种方式引入
import QRCode from 'qrcode';

const data = reactive({
  deviceId: '',
  deviceName: '',
  tableData: [],
  total: 0,
  pageSize: 5,
  pageNum: 1,
});

// 生成二维码
const generateQRCode = (text) => {
  return new Promise((resolve, reject) => {
    QRCode.toDataURL(text, function (err, url) {
      if (err) reject(err);
      else resolve(url);
    });
  });
}

// 处理二维码加载
const handleQRCodeLoad = async (row) => {
  if (!row.qrCode) { // 确保qrCode不存在时才生成
    const customerInfo = JSON.stringify({
      deviceId: row.deviceId,
      deviceName: row.deviceName,
      depreciationRate: row.depreciationRate,
      electricityRate: row.electricityRate,
    });
    const qrCodeUrl = await generateQRCode(`http://localhost:5173/device?data=${encodeURIComponent(customerInfo)}`);
    row.qrCode = qrCodeUrl;
  }
}

// 初始化页面加载
const load = () => {
  request.get("/deviceInfo/selectPage", {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      deviceId: data.deviceId,
      deviceName: data.deviceName,
    }
  }).then(res => {
    data.tableData = res.data?.list || [];
    data.total = res.data?.total || 0;
    data.tableData.forEach(handleQRCodeLoad); // 加载二维码
  });
};
load();

// 翻页事件
const handleCurrentChange = () => {
  load();
};
const handleSizeChange = () => {
  load();
};

// 重置事件
const reset = () => {
  data.deviceId = '';
  data.deviceName = '';
  load();
};
</script>

<style scoped>
.card {
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
