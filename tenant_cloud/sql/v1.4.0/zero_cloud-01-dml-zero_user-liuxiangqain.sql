update zero_cloud.zero_user set name = real_name where name is null or name = '' and user_type = 1;
