package com.peraglobal.mongodb.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.common.IDGenerate;
import com.peraglobal.mongodb.model.Metadata;
import com.peraglobal.mongodb.repository.MetadataRepository;

/**
 *  <code>MetadataService.java</code>
 *  <p>功能：元数据功能 Service
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2016-12-19
 *  </br>最后修改人 无
 */
@Service
public class MetadataService {
	
	@Autowired
    private MetadataRepository metadataRepository;

	
	/**
	 * 根据组爬虫 ID 获得元数据列表
	 * @param crawlerId 爬虫 ID
	 * @return List<Metadata> 元数据列表
	 */
	public List<Metadata> getMetadatasByCrawlerId(String crawlerId) throws Exception {
		return metadataRepository.findByCrawlerId(crawlerId);
	}
	
	/**
	 * 根据 ID 查询元数据
	 * @param metadataId 元数据 ID
	 * @return Metadata 元数据
	 */
	public Metadata getMetadata(String metadataId) throws Exception {
		return metadataRepository.findByMetadataId(metadataId);
	}

	/**
	 * 根据 MD5 码 查询元数据
	 * @param md MD5 码
	 * @return Metadata 元数据
	 */
	public Metadata getMetadataByMd(String md) throws Exception {
		return metadataRepository.findByMd(md);
	}
	
	/**
	 * 创建元数据
	 * @param metadata 元数据对象
	 * @throws Exception
	 */
	public String createMetadata(Metadata metadata) throws Exception {
		Metadata meta = metadataRepository.findByMd(metadata.getMd());
		if(meta == null) {
			metadata.setMetadataId(IDGenerate.uuid());
			metadata.setCreateTime(new Date());
			metadata.setUpdateTime(new Date());
			metadataRepository.save(metadata);
			return metadata.getMetadataId();
		}
		return null;
	}

	/**
	 * 通过移除元数据 ID 删除对象
	 * @param metadataId 元数据 ID
	 * @throws Exception
	 */
	public void removeMetadata(String metadataId) throws Exception {
		metadataRepository.deleteByMetadataId(metadataId);
	}
	
	/**
	 * 通过爬虫 ID 删除对象
	 * @param crawlerId 爬虫 ID
	 * @throws Exception
	 */
	public void removeMetadataByCrawlerId(String crawlerId) throws Exception {
		metadataRepository.deleteByCrawlerId(crawlerId);
	}
	
}
