using System;
using System.ComponentModel;
using System.Windows.Input;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.Entities;
using MBI.Logic.Serialization;
using MBI.UI.Commands;
using Microsoft.Win32;
using System.Linq;

namespace MBI.UI.ViewModels
{
	public class MainViewModel : ViewModelBase
	{
		#region Dependencies

		private readonly IAssemblyParser _assemblyParser;
		private readonly IDNAAssembler _dnaAssembler;
		private readonly IScaffoldSerializer _scaffoldSerializer;

		#endregion

		#region Properties

		public ICommand ChooseFile
		{
			get { return new Command(GetInputFile); }
		}

		public ICommand Assemble
		{
			get { return new Command(ExecuteAssemblance); }
		}

		private string _inputFileName;
		public string InputFileName
		{
			get { return _inputFileName; }
			set
			{
				_inputFileName = value;
				NotifyPropertyChanged("InputFileName");
			}
		}

		private string _outputFileName;
		public string OutputFileName
		{
			get { return _outputFileName; }
			set
			{
				_outputFileName = value;
				NotifyPropertyChanged("OutputFileName");
			}
		}

		private string _errorMessage;
		public string ErrorMessage
		{
			get { return _errorMessage; }
			set
			{
				_errorMessage = value;
				NotifyPropertyChanged("ErrorMessage");
			}
		}

		public DNAAssembly Assembly { get; set; }

		#endregion

		#region Constructor

		public MainViewModel(IAssemblyParser assemblyParser, IDNAAssembler dnaAssembler, IScaffoldSerializer scaffoldSerializer)
		{
			_assemblyParser = assemblyParser;
			_dnaAssembler = dnaAssembler;
			_scaffoldSerializer = scaffoldSerializer;
		}

		#endregion

		#region Private methods

		private void GetInputFile()
		{
			try
			{
				var dialog = new OpenFileDialog();
				dialog.ShowDialog();

				if (!string.IsNullOrEmpty(dialog.FileName))
				{
					InputFileName = dialog.SafeFileName;
					Assembly = _assemblyParser.Parse(dialog.OpenFile());
				}
			}
			catch (Exception exception)
			{
				ErrorMessage = exception.Message;
			}
		}

		private void ExecuteAssemblance()
		{
			try
			{
				var scaffolds = _dnaAssembler.Assemble(Assembly.Contigs, Assembly.PairedEndTags);

				var dialog = new SaveFileDialog();
				dialog.ShowDialog();

				if (!string.IsNullOrEmpty(dialog.FileName))
				{
					_scaffoldSerializer.Serialize(scaffolds.First(), dialog.OpenFile());
					OutputFileName = dialog.SafeFileName;
				}
			}
			catch (Exception exception)
			{
				ErrorMessage = exception.Message;
			}
		}

		#endregion
	}
}
