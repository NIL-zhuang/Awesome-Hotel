<template>
    <a-carousel autoplay>
        <div><h3>展示属于会员的优惠券</h3></div>
        <div><h3>2</h3></div>
        <div><h3>3</h3></div>
        <div><h3>4</h3></div>
    </a-carousel>
</template>

<script>
    export default {
        name: "membershipCoupon"
    }
</script>

<style scoped>
    /* For demo */
    .ant-carousel {
        width: 600px;
    }

    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 340px;
        line-height: 340px;
        background: #83c2f8;
        overflow: hidden;
    }

    .ant-carousel >>> .slick-slide h3 {
        color: #fff;
    }
</style>