<template>
	<div>
		<div style="margin: 10px 0">
			<el-input style="width:200px" suffix-icon="el-icon-search" placeholder="请输入名称" class="ml-5" v-model="name"></el-input>
			<el-button style="width:100px" type="warning" @click="reset" class="role_button">重置</el-button>
			<el-button style="width:100px" type="primary" @click="load" class="role_button">搜索</el-button>
		</div>
		<div style="padding: 20px 0; display: flex; gap: 10px;" class="button-container">
			<el-button id="role-button" type="primary" @click="handleAdd">
				新增角色<i class="el-icon-circle-plus-outline"></i>
			</el-button>
			<el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red" title="您确定批量删除这些数据吗？" @confirm="delBatch">
				<el-button type="danger" slot="reference" id="delete-button">
					批量删除 <i class="el-icon-remove-outline"></i>
				</el-button>
			</el-popconfirm>
		</div>

		<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="id" label="ID" width="80"></el-table-column>
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column prop="description" label="描述"></el-table-column>
			<el-table-column prop="flag" label="唯一标识"></el-table-column>
      <el-table-column prop="createTime" label="时间"></el-table-column>
 <el-table-column prop="updateTime" label="最后修改时间"></el-table-column>
 <el-table-column prop="isDeleted" label="是否能被删除"></el-table-column>
			<el-table-column label="操作" width="280" align="center">
				<template slot-scope="scope">
					<!-- <el-button type="info" @click="selectMenu(scope.row)">分配菜单 <i class="el-icon-menu"></i>
					</el-button>
					<el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i>
					</el-button> -->
					<!-- <el-popconfirm
								   class="ml-5"
								   confirm-button-text='确定'
								   cancel-button-text='我再想想'
								   icon="el-icon-info"
								   icon-color="red"
								   title="您确定删除吗？"
								   @confirm="DeleteEdit(scope.row.id)">
						<el-button type="danger" slot="reference" @click="DeleteEdit(scope.row.id)">删除 <i class="el-icon-remove-outline"></i>
						</el-button>
					</el-popconfirm> -->
						<el-button type="danger" @click="DeleteEdit(scope.row.id)" slot="reference" class="Userel-button">删除<i class="el-icon-remove-outline"> </i>
						</el-button>


				</template>
			</el-table-column>
		</el-table>
		<div style="padding: 10px 0">
			<el-pagination
						   @size-change="handleSizeChange"
						   @current-change="handleCurrentChange"
						   :current-page="pageNum"
						   :page-sizes="[2, 5, 10, 20]"
						   :page-size="pageSize"
						   layout="total, sizes, prev, pager, next, jumper"
						   :total="total">
			</el-pagination>
		</div>

		<el-dialog title="字典信息栏" :visible.sync="dialogFormVisible" width="30%">
			<el-form label-width="80px" size="small">
				<el-form-item label="字典名称">
					<el-input v-model="form.name" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="字典值">
					<el-input v-model="form.value" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="类型">
					<el-input v-model="form.type" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="save">确 定</el-button>
			</div>
		</el-dialog>
<!-- 
		<el-dialog title="菜单分配" :visible.sync="menuDialogVis" width="30%">
			<el-tree
					 :props="props"
					 :data="menuData"
					 show-checkbox
					 node-key="id"
					 ref="tree"
					 :default-expanded-keys="expends"
					 :default-checked-keys="checks">
				<span class="custom-tree-node" slot-scope="{ node, data }">
					<span>
						<i :class="data.icon"></i> {{ data.name }}
					</span>
				</span>
			</el-tree>
			<div slot="footer" class="dialog-footer">
				<el-button @click="menuDialogVis = false">取 消</el-button>
				<el-button type="primary" @click="saveRoleMenu">确 定</el-button>
			</div>
		</el-dialog> -->
	</div>
</template>

<script>
export default {
  name: "Dict",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      dialogFormVisible: false,
      // menuDialogVis: false,
      //  menuData: [],
      multipleSelection: [],

      props: {
        label: "name"
      },
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: "",
      ids: []
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request
        .get("/dict/findPage", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name
          }
        })
        .then(res => {
          this.tableData = res.data.records;
          this.total = parseInt(res.data.total);
        });

      // this.request.get("/menu/ids").then(r => {
      //   this.ids = r.data;
      // });
    },
    save() {
      this.request.post("/role/save", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      });
    },
    // saveRoleMenu() {
    //   this.request
    //     .post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys())
    //     .then(res => {
    //       if (res.code === "200") {
    //         this.$message.success("绑定成功");
    //         this.menuDialogVis = false;

    //         // 操作管理员角色后需要重新登录
    //         if (this.roleFlag === "ROLE_ADMIN") {
    //           this.$store.commit("logout");
    //         }
    //       } else {
    //         this.$message.error(res.msg);
    //       }
    //     });
    // },
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    DeleteEdit(id) {
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          request
            .post("/role/deleteById/" + id)
            .then(res => {
              if (res.code === 200) {
                this.$message.success("删除成功");
                this.load();
              } else {
                this.$message.error("删除失败");
                this.load();
              }
            })
            .catch(() => {
              // 发生错误
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          // 用户取消删除操作
          this.$message.info("已取消删除操作");
        });
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id); // [{}, {}, {}] => [1,2,3]
      this.request.post("/role/del/batch", ids).then(res => {
        if (res.code === "200") {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },
    reset() {
      this.name = "";
      this.load();
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    }
    // async selectMenu(role) {
    //   this.roleId = role.id;
    //   this.roleFlag = role.flag;

    // 请求菜单数据
    //     this.request.get("/menu").then(res => {
    //       this.menuData = res.data;

    //       // 把后台返回的菜单数据处理成 id数组
    //       this.expends = this.menuData.map(v => v.id);
    //     });

    //     this.request.get("/role/roleMenu/" + this.roleId).then(res => {
    //       this.checks = res.data;
    //       this.ids.forEach(id => {
    //         if (!this.checks.includes(id)) {
    //           // 可能会报错：Uncaught (in promise) TypeError: Cannot read properties of undefined (reading 'setChecked')
    //           this.$nextTick(() => {
    //             this.$refs.tree.setChecked(id, false);
    //           });
    //         }
    //       });
    //       this.menuDialogVis = true;
    //     });
    //   }
    // }
  }
};
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>