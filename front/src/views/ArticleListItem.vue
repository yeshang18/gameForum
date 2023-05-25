<template>
  <div class="article-item">
    <div class="article-item-inner">
        <div class="article-body">
            <div class="user-info">
                <Avatar :width="30"></Avatar>
            </div>
        </div>
    </div>
    
  </div>
</template>

<script setup>
const props =defineProps({
    data:{
        type:Object
    }
})
</script>

<style lang="scss" scoped>
</style>
