$(function () {
    getGoodsList();
});

var goodsCategory = 0;
var seckillStatus = 0;
var pageNum = 1;
var ele = $('#goodsListHolder');


function getGoodsList() {
    showMoreGoodsButton();
    $.ajax({
        url: '/seckill/goods/list',
        data: {
            'goodsCategory': goodsCategory,
            'seckillStatus': seckillStatus,
            'pageNum': pageNum
        },
        success: function (res) {
            if (res.code != null && res.code == 200) {
                noMoreGoods();
            }
            ele.append(res);
            pageNum++;
        }
    })
}

function setCategory(c, title) {
    reSet();
    goodsCategory = c;
    getGoodsList();
    $('#category').html(title + '<span class="caret"></span>');
}

function setStatus(s, title) {
    reSet();
    seckillStatus = s;
    getGoodsList();
    $('#status').html(title + "<span class=\"caret\"></span>");
}

function reSet() {
    pageNum = 1;
    ele.empty();
}

function noMoreGoods() {
    $('#bottom').empty();
    $('#bottom').html('<div class="alert alert-info" role="alert">没有找到对应商品或已经被全部加载，请期待后续上架！^_^</div>');
}

function showMoreGoodsButton() {
    $('#bottom').empty();
    $('#bottom').html('<button type="button" class="btn btn-success" onclick="getGoodsList();">加载更多...</button>');
}

function jumpToDetail(goodsId) {
    alert(goodsId);
}