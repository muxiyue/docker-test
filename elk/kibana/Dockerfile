FROM hub.c.163.com/muxiyue/kibana:6.4.0

#WORKDIR  /usr/share/kibana

# 使用https://github.com/anbai-inc/Kibana_Hanization汉化
#RUN wget -P /usr/share/Kibana_Hanization https://codeload.github.com/anbai-inc/Kibana_Hanization/zip/master

# 汉化处理
RUN cd /tmp \
&& curl https://codeload.github.com/anbai-inc/Kibana_Hanization/tar.gz/master -o Kibana_Hanization-master.tar.gz --progress \
&& tar -xvf Kibana_Hanization-master.tar.gz \
&& cd Kibana_Hanization-master && python main.py /usr/share/kibana && cd /usr/share/kibana



################
###############
##############