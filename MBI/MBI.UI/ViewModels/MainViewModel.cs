using System;
using System.IO;
using System.Threading;
using System.Windows.Input;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.Entities;
using MBI.Logic.Infrastructure;
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

		#region Fields

		private ProgressIndication _progressIndication;
		private CancellationTokenSource _cancellationTokenSource;

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

		public ICommand Cancel
		{
			get { return new Command(CancelAssemblance); }
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

		public string Progress
		{
			get { return _progressIndication != null ? string.Format("{0}%", (_progressIndication.Progress * 100.0).ToString("F")) : string.Empty; }
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
				if (Assembly == null)
				{
					throw new InvalidOperationException("No input file specified");
				}

				var dialog = new SaveFileDialog();
				dialog.ShowDialog();

				if (string.IsNullOrEmpty(dialog.FileName))
				{
					throw new IOException("Please choose output file");
				}

				if (_cancellationTokenSource != null)
				{
					_cancellationTokenSource.Dispose();
				}

				_progressIndication = new ProgressIndication(() => NotifyPropertyChanged("Progress"));
				_cancellationTokenSource = new CancellationTokenSource();

				new Thread(() => Assemblee(dialog.OpenFile(), dialog.SafeFileName, _progressIndication, _cancellationTokenSource.Token)).Start();
			}
			catch (Exception exception)
			{
				ErrorMessage = exception.Message;
			}
		}

		private void Assemblee(Stream outputFileStream, string outputFileName, ProgressIndication progressIndication, CancellationToken cancellationToken)
		{
			try
			{
				var scaffolds = _dnaAssembler.Assemble(Assembly.Contigs, Assembly.PairedEndTags, progressIndication, cancellationToken);
				_scaffoldSerializer.Serialize(scaffolds.First(), outputFileStream);

				OutputFileName = outputFileName;
			}
			catch (OperationCanceledException)
			{
				ErrorMessage = "Operation cancelled";
			}
			catch (Exception exception)
			{
				ErrorMessage = exception.Message;
			}
		}

		private void CancelAssemblance()
		{
			if (_cancellationTokenSource != null)
			{
				_cancellationTokenSource.Cancel();
			}
			else
			{
				ErrorMessage = "No operation in progress";
			}
		}

		#endregion
	}
}
