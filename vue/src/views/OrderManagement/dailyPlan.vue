<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="searchParams.ticketID" style="width: 260px;margin-right: 10px" placeholder="工单编号"
                :prefix-icon="Search" size="medium"/>
      <el-input v-model="searchParams.worker" style="width: 260px;margin-right: 10px" placeholder="工人名称"
                :prefix-icon="Search" size="medium"/>
      <el-button type="primary" style="margin-left:10px" @click="loadTickets">查询</el-button>
      <el-button type="info" @click="resetSearch">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 10px">
      <el-table :data="ticketsData" style="width: 150%">
        <el-table-column prop="ticketID" label="工单编号"></el-table-column>
        <el-table-column prop="planID" label="计划编号"></el-table-column>
        <el-table-column prop="productName" label="产品名称"></el-table-column>
        <el-table-column prop="workshop" label="车间"></el-table-column>
        <el-table-column prop="team" label="班组"></el-table-column>
        <el-table-column prop="worker" label="工人名字"></el-table-column>
        <el-table-column prop="grade" label="工人工级"></el-table-column>
        <el-table-column prop="process" label="工序" width="150"></el-table-column>
        <el-table-column prop="number" label="所需完成的数量" width="150"></el-table-column>
        <el-table-column prop="startDate" label="开始日期"></el-table-column>
        <el-table-column prop="endDate" label="结束日期" ></el-table-column>

        <el-table-column label="工人二维码" width="200">
          <template #default="scope">
            <img :src="scope.row.qrCode" style="width: 100px; height: 100px;" />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination
          v-model:currentPage="searchParams.pageNum"
          v-model:pageSize="searchParams.pageSize"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :page-sizes="[5, 10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script setup>
import {ref, reactive, watch} from "vue";
import {Search} from "@element-plus/icons-vue";
import request from "@/utils/request";
import QRCode from 'qrcode';

const searchParams = reactive({
  ticketID: '',
  worker: '',
  pageNum: 1,
  pageSize: 5,
});

const ticketsData = ref([]);
const total = ref(0);

// 生成二维码
const generateQRCode = (text) => {
  return new Promise((resolve, reject) => {
    QRCode.toDataURL(text, function (err, url) {
      if (err) reject(err);
      else resolve(url);
    });
  });
}

// 监听tableData变化，生成二维码
watch(ticketsData, (newData) => {
  newData.forEach((row) => {
    if (!row.qrCode) {
      handleQRCodeLoad(row);
    }
  });
}, { deep: true });

const handleQRCodeLoad = async (row) => {
  if (!row.qrCode) { // 确保qrCode不存在时才生成
    const dailyPlanInfo = JSON.stringify({

      productName: row.productName,
      worker: row.worker,
      process: row.process,
      number: row.number,
      startDate: row.startDate,
      endDate: row.endDate
    });
    try {
      const url = await generateQRCode(`http://localhost:5173/qrcustomer1?data=${encodeURIComponent(dailyPlanInfo)}`);
      row.qrCode = url;
      console.log('二维码中的字符串:', dailyPlanInfo);
    } catch (error) {
      console.error('生成二维码失败:', error);
    }
  }
}

// 初始化页面加载
const loadTickets = () => {
  request.get("/ticket/selectPage", {
    params: {
      pageNum: searchParams.pageNum,
      pageSize: searchParams.pageSize,
      ticketID: searchParams.ticketID,
      worker: searchParams.worker,
    }
  }).then(res => {
    ticketsData.value = res.data?.list || [];
    total.value = res.data?.total || 0;
  }).catch(error => {
    console.error('加载工单数据失败:', error);
  });
};
loadTickets();

// 翻页事件
const handleCurrentChange = (newPage) => {
  searchParams.pageNum = newPage;
  loadTickets();
};
const handleSizeChange = (newSize) => {
  searchParams.pageSize = newSize;
  loadTickets();
};

// 重置搜索参数
const resetSearch = () => {
  searchParams.ticketID = '';
  searchParams.worker = '';
  searchParams.pageNum = 1;
  searchParams.pageSize = 5;
  loadTickets();
};
</script>

<style scoped>
.card {
  margin-bottom: 10px;
}
</style>