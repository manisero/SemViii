﻿using System.Collections.Generic;
using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance
{
	public interface IDNAAssembler
	{
		IList<Scaffold> Assemble(string[] contigs, PairedEndTag[] pairedEndTags);
	}
}
