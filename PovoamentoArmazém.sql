CREATE SCHEMA IF NOT EXISTS `ArmazemDSS` DEFAULT CHARACTER SET utf8 ;
USE `ArmazemDSS`;

-- O povoamneto deve ser feito depois de criar as tabelas. O programa deve ser aberto uma vez antes sem realizar operações.
INSERT INTO prateleiras
	(CodPrateleira,Tamanho,Estado,Localizacao,CodPalete)
	VALUES 
		('PR1',2.0,'L',3,NULL),
        ('PR2',2.0,'L',4,NULL),
        ('PR3',2.0,'L',5,NULL),
        ('PNR1',2.0,'L',6,NULL),
        ('PNR2',2.0,'L',7,NULL),
        ('PNR3',2.0,'L',9,NULL),
        ('PNR4',2.0,'L',10,NULL),
        ('PNR5',2.0,'L',11,NULL),
        ('PNR6',2.0,'L',12,NULL),
        ('PNR7',2.0,'L',13,NULL)
        ON DUPLICATE KEY UPDATE
        CodPrateleira = VALUES(CodPrateleira),
        Tamanho = VALUES(Tamanho),
        Estado = VALUES(Estado),
        Localizacao = VALUES(Localizacao),
        CodPalete = VALUES(CodPalete);
        
INSERT INTO robot
	(CodRobot,Disponibilidade,Localizacao,Base,CodPalete)
	VALUES 
		('R1',true,16,16,NULL),
		('R2',true,16,16,NULL)
	ON DUPLICATE KEY UPDATE 
		CodRobot= VALUES(CodRobot),
        Disponibilidade= VALUES(Disponibilidade),
        Localizacao= VALUES(Localizacao),
        Base= VALUES(Base),
        CodPalete= VALUES(CodPalete);