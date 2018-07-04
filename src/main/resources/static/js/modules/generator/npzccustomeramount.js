$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'generator/npzccustomeramount/list',
        datatype: "json",
        colModel: [			
			{ label: 'cusAmountId', name: 'cusAmountId', index: 'cus_amount_id', width: 50, key: true },
			{ label: ' 会员ID', name: 'customerId', index: 'customer_id', width: 80 }, 			
			{ label: '会员余额', name: 'customerAmount', index: 'customer_amount', width: 80 }, 			
			{ label: '创建时间 ', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '修改时间', name: 'modityTime', index: 'modity_time', width: 80 }, 			
			{ label: '删除标记(0.未删除1.已删除) ', name: 'delFlag', index: 'del_flag', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		npZcCustomerAmount: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.npZcCustomerAmount = {};
		},
		update: function (event) {
			var cusAmountId = getSelectedRow();
			if(cusAmountId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(cusAmountId)
		},
		saveOrUpdate: function (event) {
			var url = vm.npZcCustomerAmount.cusAmountId == null ? "generator/npzccustomeramount/save" : "generator/npzccustomeramount/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.npZcCustomerAmount),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var cusAmountIds = getSelectedRows();
			if(cusAmountIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "generator/npzccustomeramount/delete",
                    contentType: "application/json",
				    data: JSON.stringify(cusAmountIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(cusAmountId){
			$.get(baseURL + "generator/npzccustomeramount/info/"+cusAmountId, function(r){
                vm.npZcCustomerAmount = r.npZcCustomerAmount;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});