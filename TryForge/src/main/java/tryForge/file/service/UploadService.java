package tryForge.file.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tryForge.file.dao.UploadDao;
import tryForge.vo.FileStorage;

@Service
public class UploadService {
	@Autowired(required = false)
	private UploadDao dao;
	
@Value("${file.upload}")
private String path;
	
	public FileStorage getFiles(FileStorage file) {
	//	file.setFname();
		return file;
	}

	public String uploadFile(FileStorage upload) {
		int chk = 0;
		String msg = "";
		MultipartFile[] mpfs = upload.getFiles();
		
		if(mpfs!=null && mpfs.length>0) {
			
			try {
				for(MultipartFile mpf:mpfs) {
				
					String fname = mpf.getOriginalFilename(); // 이름추출
					String ftype = mpf.getContentType(); // image/jpeg 형식으로 추출
					long fsize = mpf.getSize(); // 파일크기 추출(byte)
					
					// 확장자만 추출
					String extension = "";
					if(ftype!=null) {
						int idx = ftype.lastIndexOf('/'); // '/'의 순서 저장
						if(idx > 0) {
							extension = ftype.substring(idx + 1);
						}
					}
					
					// byte --> mb
					double fsizeMB = (double)fsize / (1024 * 1024);
					
					// File 경로 지정해서 MultipartFile에 담긴 파일 저장
					mpf.transferTo(new File(path+fname));
					chk += dao.uploadFile(new FileStorage(fname, path, extension, fsizeMB, upload.getDescription()));
				}
			} catch (IllegalStateException e) {
				System.out.println("#파일업로드 예외1:"+e.getMessage());
				msg+="#파일업로드 예외1:"+e.getMessage()+"\\n";
			} catch (IOException e) {
				System.out.println("#파일업로드 예외2:"+e.getMessage());
				msg+="#파일업로드 예외2:"+e.getMessage()+"\\n";
			} catch(Exception e) {
				System.out.println("#기타 예외3:"+e.getMessage());
				msg+="#기타 예외3:"+e.getMessage()+"\\n";
			}
		}
		msg = "파일 "+ chk+"건 등록 완료";
		return msg;
	}
}
