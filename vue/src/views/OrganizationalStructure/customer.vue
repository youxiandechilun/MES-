<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="data.contractNumber" style="width: 260px;margin-right: 10px" placeholder="请输入合同编号查询"
                :prefix-icon="Search"/>
      <el-input v-model="data.customerName" style="width: 260px;margin-right: 10px" placeholder="请输入客户名称查询"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table :data="data.tableData" style="width: 100%">
        <el-table-column prop="contractNumber" label="合同编号"></el-table-column>
        <el-table-column prop="contractName" label="合同名称"></el-table-column>
        <el-table-column prop="customerCode" label="客户编号"></el-table-column>
        <el-table-column prop="customerName" label="客户名称"></el-table-column>
        <el-table-column prop="customerPhone" label="客户电话"></el-table-column>
        <el-table-column label="二维码">
          <template #default="scope">
            <img :src="scope.row.qrCode" style="width: 100px; height: 100px;" />
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:pageSize="data.pageSize"
          v-model:currentPage="data.pageNum"
          @current-change="handelCurrentChange"
          @size-change="handelSizeChange"
          :page-sizes="[5,10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="data.total">
      </el-pagination>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from "vue";
import { Search } from "@element-plus/icons-vue";
import request from "@/utils/request";
import QRCode from 'qrcode';

const data = reactive({
  contractNumber: '',
  customerName: '',
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
      contractNumber: row.contractNumber,
      customerName: row.customerName,
      customerCode: row.customerCode,
      customerPhone: row.customerPhone,
    });
    const qrCodeUrl = await generateQRCode(`http://localhost:5173/qrcustomer?data=${encodeURIComponent(customerInfo)}`);
    row.qrCode = qrCodeUrl;
  }
}

// 监听tableData变化，生成二维码
watch(() => data.tableData, (newData) => {
  newData.forEach((row) => {
    if (!row.qrCode) {
      handleQRCodeLoad(row);
    }
  });
}, { deep: true });



// 初始化页面加载
const load = () => {
  request.get("/customer/selectPage", {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      contractNumber: data.contractNumber,
      customerName: data.customerName,
    }
  }).then(res => {
    data.tableData = res.data?.list || [];
    data.total = res.data?.total || 0;
  });
};
load();

// 翻页事件
const handelCurrentChange = () => {
  load();
};
const handelSizeChange = () => {
  load();
};

// 重置事件
const reset = () => {
  data.contractNumber = '';
  data.customerName = '';
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