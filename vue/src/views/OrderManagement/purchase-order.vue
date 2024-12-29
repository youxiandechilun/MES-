<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="searchParams.contractNumber" style="width: 260px;margin-right: 10px" placeholder="请输入合同编号查询"
                :prefix-icon="Search"/>
      <el-input v-model="searchParams.productName" style="width: 260px;margin-right: 10px" placeholder="请输入产品名称查询"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="loadOrders">查询</el-button>
      <el-button type="info" @click="resetSearch">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table :data="orders" style="width: 100%">

          <el-table-column prop="orderID" label="订单编号"></el-table-column>
          <el-table-column prop="customerID" label="客户编号"></el-table-column>
          <el-table-column prop="contractNumber" label="合同编号"></el-table-column>
          <el-table-column prop="productName" label="产品名称"></el-table-column>
          <el-table-column prop="orderType" label="订单类型"></el-table-column>
          <el-table-column prop="productSum" label="产品数量"></el-table-column>
          <el-table-column prop="startTime" label="开始时间"></el-table-column>
          <el-table-column prop="endTime" label="结束时间"></el-table-column>
          <el-table-column prop="status" label="订单状态"></el-table-column>
          <el-table-column prop="totalAmount" label="总金额"></el-table-column>
        </el-table>
      </div>
    </div>
    <div class="card">
      <el-pagination
          v-model:pageSize="pageSize"
          v-model:currentPage="currentPage"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :page-sizes="[5,10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script setup>
import {ref, reactive} from "vue";
import {Search} from "@element-plus/icons-vue";
import request from "@/utils/request";

const searchParams = reactive({
  contractNumber: '',
  productName: '',
});

const orders = ref([]);
const total = ref(0);
const pageSize = ref(5);
const currentPage = ref(1);

// 加载订单数据
const loadOrders = () => {
  request.get("/order/selectPage2", {
    params: {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      contractNumber: searchParams.contractNumber,
      productName: searchParams.productName,
    }
  }).then(res => {
    orders.value = res.data?.list || [];
    total.value = res.data?.total || 0;
  });
};
loadOrders();

// 翻页事件
const handleCurrentChange = () => {
  loadOrders();
};
const handleSizeChange = () => {
  loadOrders();
};

// 重置搜索
const resetSearch = () => {
  searchParams.contractNumber = '';
  searchParams.productName = '';
  loadOrders();
};
</script>

<style scoped>
.card {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>