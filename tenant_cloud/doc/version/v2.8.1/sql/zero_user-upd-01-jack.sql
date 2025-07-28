#设置之前的数据为空的area字段为86
UPDATE zero_cloud.zero_user
SET area = '86'
WHERE area IS NULL or area = '';
